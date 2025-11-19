package com.BancoFinanceiro.BancoMalvader.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.BancoFinanceiro.BancoMalvader.model.dto.ClienteCadastroRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.ClienteCadastroResponse;
import com.BancoFinanceiro.BancoMalvader.model.entity.Cliente;
import com.BancoFinanceiro.BancoMalvader.model.entity.Endereco;
import com.BancoFinanceiro.BancoMalvader.model.entity.Usuario;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusAprovacao;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoUsuario;
import com.BancoFinanceiro.BancoMalvader.repository.ClienteRepository;
import com.BancoFinanceiro.BancoMalvader.repository.EnderecoRepository;
import com.BancoFinanceiro.BancoMalvader.repository.UsuarioRepository;
import com.BancoFinanceiro.BancoMalvader.service.ClienteCadastroService;
import com.BancoFinanceiro.BancoMalvader.util.CpfValidator;
import com.BancoFinanceiro.BancoMalvader.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do serviço de cadastro de clientes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteCadastroServiceImpl implements ClienteCadastroService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;
    private final CpfValidator cpfValidator;

    @Override
    @Transactional
    public ClienteCadastroResponse cadastrarCliente(ClienteCadastroRequest cadastroRequest) {
        log.info("Iniciando cadastro de cliente: {}", cadastroRequest.getEmail());

        try {
            // Validações iniciais
            validarDadosCadastro(cadastroRequest);

            // Cria endereço
            Endereco endereco = criarEndereco(cadastroRequest);
            Endereco enderecoSalvo = enderecoRepository.save(endereco);

            // Cria usuário base
            Usuario usuario = criarUsuario(cadastroRequest);
            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            // Cria cliente
            Cliente cliente = criarCliente(cadastroRequest, usuarioSalvo, enderecoSalvo);
            Cliente clienteSalvo = clienteRepository.save(cliente);

            log.info("Cliente cadastrado com sucesso - ID: {}, Nome: {}",
                    clienteSalvo.getId(), clienteSalvo.getNome());

            return new ClienteCadastroResponse(
                    clienteSalvo.getId(),
                    clienteSalvo.getNome(),
                    clienteSalvo.getEmail(),
                    clienteSalvo.getCpf(),
                    clienteSalvo.getStatusAprovacao()
            );

        } catch (Exception e) {
            log.error("Erro ao cadastrar cliente: {}", e.getMessage());
            throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ClienteCadastroResponse aprovarCadastro(Long clienteId, Long funcionarioId) {
        log.info("Aprovando cadastro do cliente ID: {} pelo funcionario ID: {}", clienteId, funcionarioId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setStatusAprovacao(StatusAprovacao.APROVADO);
        cliente.setMotivoReprovacao(null);
        Cliente clienteAtualizado = clienteRepository.save(cliente);

        log.info("Cadastro aprovado para cliente ID: {}", clienteId);

        return new ClienteCadastroResponse(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getEmail(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getStatusAprovacao()
        );
    }

    @Override
    @Transactional
    public ClienteCadastroResponse reprovarCadastro(Long clienteId, Long funcionarioId, String motivo) {
        log.info("Reprovando cadastro do cliente ID: {} pelo funcionario ID: {}", clienteId, funcionarioId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setStatusAprovacao(StatusAprovacao.REPROVADO);
        cliente.setMotivoReprovacao(motivo);
        Cliente clienteAtualizado = clienteRepository.save(cliente);

        log.info("Cadastro reprovado para cliente ID: {}", clienteId);

        return new ClienteCadastroResponse(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getEmail(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getStatusAprovacao()
        );
    }

    @Override
    public ClienteCadastroResponse buscarStatusCadastro(Long clienteId) {
        log.debug("Buscando status de cadastro do cliente ID: {}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return new ClienteCadastroResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getStatusAprovacao()
        );
    }

    /**
     * Valida dados do cadastro antes de criar o cliente
     */
    private void validarDadosCadastro(ClienteCadastroRequest cadastroRequest) {
        // Valida email único
        if (usuarioRepository.existsByEmail(cadastroRequest.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Valida CPF único
        if (usuarioRepository.existsByCpf(cadastroRequest.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // Valida CPF
        if (!cpfValidator.isValid(cadastroRequest.getCpf())) {
            throw new RuntimeException("CPF inválido");
        }

        // Valida senha
        passwordValidator.isValid(cadastroRequest.getSenha());

        // Valida idade mínima (18 anos)
        if (java.time.Period.between(cadastroRequest.getDataNascimento(), java.time.LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("Cliente deve ter pelo menos 18 anos");
        }
    }

    /**
     * Cria entidade Endereco a partir do DTO
     */
    private Endereco criarEndereco(ClienteCadastroRequest cadastroRequest) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(cadastroRequest.getLogradouro());
        endereco.setNumero(cadastroRequest.getNumero());
        endereco.setComplemento(cadastroRequest.getComplemento());
        endereco.setBairro(cadastroRequest.getBairro());
        endereco.setCidade(cadastroRequest.getCidade());
        endereco.setEstado(cadastroRequest.getEstado());
        endereco.setCep(cadastroRequest.getCep());
        return endereco;
    }

    /**
     * Cria entidade Usuario a partir do DTO
     */
    private Usuario criarUsuario(ClienteCadastroRequest cadastroRequest) {
        Usuario usuario = new Usuario();
        usuario.setEmail(cadastroRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroRequest.getSenha()));
        usuario.setNome(cadastroRequest.getNome());
        usuario.setCpf(cadastroRequest.getCpf());
        usuario.setTelefone(cadastroRequest.getTelefone());
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        usuario.setAtivo(true);
        usuario.setMfaHabilitado(false);
        return usuario;
    }

    /**
     * Cria entidade Cliente a partir do DTO
     */
    private Cliente criarCliente(ClienteCadastroRequest cadastroRequest, Usuario usuario, Endereco endereco) {
        Cliente cliente = new Cliente();
        cliente.setId(usuario.getId()); // Herda ID do usuário
        cliente.setEmail(usuario.getEmail());
        cliente.setSenha(usuario.getSenha());
        cliente.setNome(usuario.getNome());
        cliente.setCpf(usuario.getCpf());
        cliente.setTelefone(usuario.getTelefone());
        cliente.setTipoUsuario(usuario.getTipoUsuario());
        cliente.setAtivo(usuario.getAtivo());
        cliente.setMfaHabilitado(usuario.getMfaHabilitado());
        cliente.setDataNascimento(cadastroRequest.getDataNascimento());
        cliente.setProfissao(cadastroRequest.getProfissao());
        cliente.setRendaMensal(cadastroRequest.getRendaMensal());
        cliente.setPerfilRisco(cadastroRequest.getPerfilRisco());
        cliente.setEndereco(endereco);
        return cliente;
    }
}