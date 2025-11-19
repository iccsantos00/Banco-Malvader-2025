// src/pages/Auth/CriarConta.jsx - NOVO
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, User, Mail, Phone, MapPin, Lock, AlertCircle } from 'lucide-react';
import toast from 'react-hot-toast';

const CriarConta = () => {
  const [step, setStep] = useState(1);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const [dadosCliente, setDadosCliente] = useState({
    nome: '',
    cpf: '',
    dataNascimento: '',
    telefone: '',
    email: '',
    cep: '',
    logradouro: '',
    numero: '',
    bairro: '',
    cidade: '',
    estado: '',
    senhaLogin: '',
    confirmarSenhaLogin: '',
    senhaTransacao: '',
    confirmarSenhaTransacao: ''
  });

  const handleInputChange = (field, value) => {
    setDadosCliente(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const validarSenhaLogin = (senha) => {
    return senha.length >= 12 &&
           /[A-Z]/.test(senha) &&
           /[a-z]/.test(senha) &&
           /\d/.test(senha) &&
           /[!@#$%&*]/.test(senha);
  };

  const handleCriarConta = async () => {
    // Validações
    if (dadosCliente.senhaLogin !== dadosCliente.confirmarSenhaLogin) {
      toast.error('As senhas de login não coincidem');
      return;
    }

    if (dadosCliente.senhaTransacao !== dadosCliente.confirmarSenhaTransacao) {
      toast.error('As senhas de transação não coincidem');
      return;
    }

    if (!validarSenhaLogin(dadosCliente.senhaLogin)) {
      toast.error('A senha de login não atende aos requisitos de segurança');
      return;
    }

    if (!/^\d{6}$/.test(dadosCliente.senhaTransacao)) {
      toast.error('A senha de transação deve ter exatamente 6 dígitos numéricos');
      return;
    }

    setLoading(true);
    
    // Simulação de criação de conta
    setTimeout(() => {
      setLoading(false);
      setStep(2); // Ir para tela de análise
      toast.success('Conta criada com sucesso! Em análise...');
    }, 2000);
  };

  const renderStep1 = () => (
    <div className="space-y-6">
      <div className="text-center">
        <h2 className="text-2xl font-bold text-gray-900">Criar Conta Bancária</h2>
        <p className="text-gray-600 mt-2">Preencha seus dados pessoais</p>
      </div>

      <div className="grid grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Nome Completo</label>
          <div className="relative">
            <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              value={dadosCliente.nome}
              onChange={(e) => handleInputChange('nome', e.target.value)}
              className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="Seu nome completo"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">CPF</label>
          <input
            type="text"
            value={dadosCliente.cpf}
            onChange={(e) => handleInputChange('cpf', e.target.value.replace(/\D/g, ''))}
            maxLength={11}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="000.000.000-00"
          />
        </div>
      </div>

      <div className="grid grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Data Nascimento</label>
          <input
            type="date"
            value={dadosCliente.dataNascimento}
            onChange={(e) => handleInputChange('dataNascimento', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Telefone</label>
          <div className="relative">
            <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              value={dadosCliente.telefone}
              onChange={(e) => handleInputChange('telefone', e.target.value.replace(/\D/g, ''))}
              className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="(11) 99999-9999"
            />
          </div>
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">Email</label>
        <div className="relative">
          <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
          <input
            type="email"
            value={dadosCliente.email}
            onChange={(e) => handleInputChange('email', e.target.value)}
            className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="seu@email.com"
          />
        </div>
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-xl p-4">
        <div className="flex items-start">
          <AlertCircle className="text-blue-600 mt-0.5 mr-3" size={20} />
          <div className="text-sm text-blue-800">
            <strong>Informações de segurança:</strong>
            <p className="mt-1">Todos os dados serão criptografados e protegidos conforme as normas do BACEN.</p>
          </div>
        </div>
      </div>

      <button
        onClick={() => setStep(2)}
        className="w-full bg-blue-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-blue-700 transition-colors"
      >
        Continuar
      </button>
    </div>
  );

  const renderStep2 = () => (
    <div className="space-y-6">
      <div className="text-center">
        <h2 className="text-2xl font-bold text-gray-900">Endereço</h2>
        <p className="text-gray-600 mt-2">Informe seu endereço completo</p>
      </div>

      <div className="grid grid-cols-3 gap-4">
        <div className="col-span-1">
          <label className="block text-sm font-medium text-gray-700 mb-2">CEP</label>
          <input
            type="text"
            value={dadosCliente.cep}
            onChange={(e) => handleInputChange('cep', e.target.value.replace(/\D/g, ''))}
            maxLength={8}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="00000-000"
          />
        </div>
        
        <div className="col-span-2">
          <label className="block text-sm font-medium text-gray-700 mb-2">Logradouro</label>
          <div className="relative">
            <MapPin className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              value={dadosCliente.logradouro}
              onChange={(e) => handleInputChange('logradouro', e.target.value)}
              className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="Rua, Avenida, etc..."
            />
          </div>
        </div>
      </div>

      <div className="grid grid-cols-3 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Número</label>
          <input
            type="text"
            value={dadosCliente.numero}
            onChange={(e) => handleInputChange('numero', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="123"
          />
        </div>
        
        <div className="col-span-2">
          <label className="block text-sm font-medium text-gray-700 mb-2">Bairro</label>
          <input
            type="text"
            value={dadosCliente.bairro}
            onChange={(e) => handleInputChange('bairro', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="Seu bairro"
          />
        </div>
      </div>

      <div className="grid grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Cidade</label>
          <input
            type="text"
            value={dadosCliente.cidade}
            onChange={(e) => handleInputChange('cidade', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="Sua cidade"
          />
        </div>
        
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">Estado</label>
          <select
            value={dadosCliente.estado}
            onChange={(e) => handleInputChange('estado', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          >
            <option value="">Selecione</option>
            <option value="SP">São Paulo</option>
            <option value="RJ">Rio de Janeiro</option>
            <option value="MG">Minas Gerais</option>
            {/* Outros estados... */}
          </select>
        </div>
      </div>

      <div className="flex gap-4">
        <button
          onClick={() => setStep(1)}
          className="flex-1 py-3 px-4 border border-gray-300 text-gray-700 rounded-xl font-medium hover:bg-gray-50 transition-colors"
        >
          Voltar
        </button>
        <button
          onClick={() => setStep(3)}
          className="flex-1 bg-blue-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-blue-700 transition-colors"
        >
          Continuar
        </button>
      </div>
    </div>
  );

  const renderStep3 = () => (
    <div className="space-y-6">
      <div className="text-center">
        <h2 className="text-2xl font-bold text-gray-900">Segurança da Conta</h2>
        <p className="text-gray-600 mt-2">Crie suas senhas de acesso</p>
      </div>

      {/* Senha de Login */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Senha de Login (12+ caracteres)
        </label>
        <div className="relative">
          <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
          <input
            type="password"
            value={dadosCliente.senhaLogin}
            onChange={(e) => handleInputChange('senhaLogin', e.target.value)}
            className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="Mínimo 12 caracteres com maiúsculas, minúsculas, números e símbolos"
          />
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Confirmar Senha de Login
        </label>
        <input
          type="password"
          value={dadosCliente.confirmarSenhaLogin}
          onChange={(e) => handleInputChange('confirmarSenhaLogin', e.target.value)}
          className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          placeholder="Confirme sua senha de login"
        />
      </div>

      {/* Senha de Transação */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Senha de Transação (6 dígitos)
        </label>
        <input
          type="password"
          value={dadosCliente.senhaTransacao}
          onChange={(e) => handleInputChange('senhaTransacao', e.target.value.replace(/\D/g, '').slice(0, 6))}
          maxLength={6}
          className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-center text-xl font-mono"
          placeholder="000000"
        />
        <p className="text-sm text-gray-600 mt-2 text-center">
          Use esta senha de 6 dígitos para confirmar transações e compras
        </p>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Confirmar Senha de Transação
        </label>
        <input
          type="password"
          value={dadosCliente.confirmarSenhaTransacao}
          onChange={(e) => handleInputChange('confirmarSenhaTransacao', e.target.value.replace(/\D/g, '').slice(0, 6))}
          maxLength={6}
          className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-center text-xl font-mono"
          placeholder="000000"
        />
      </div>

      <div className="bg-yellow-50 border border-yellow-200 rounded-xl p-4">
        <div className="text-sm text-yellow-800">
          <strong>⚠️ ATENÇÃO:</strong>
          <ul className="mt-1 list-disc list-inside space-y-1">
            <li>A senha de login deve ter pelo menos 12 caracteres</li>
            <li>Inclua letras maiúsculas, minúsculas, números e símbolos</li>
            <li>A senha de transação deve ter exatamente 6 dígitos numéricos</li>
            <li>Guarde suas senhas em local seguro</li>
          </ul>
        </div>
      </div>

      <div className="flex gap-4">
        <button
          onClick={() => setStep(2)}
          className="flex-1 py-3 px-4 border border-gray-300 text-gray-700 rounded-xl font-medium hover:bg-gray-50 transition-colors"
        >
          Voltar
        </button>
        <button
          onClick={handleCriarConta}
          disabled={loading}
          className="flex-1 bg-green-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-green-700 transition-colors disabled:opacity-50"
        >
          {loading ? 'Criando Conta...' : 'Criar Minha Conta'}
        </button>
      </div>
    </div>
  );

  const renderStep4 = () => (
    <div className="space-y-6 text-center">
      <div className="w-16 h-16 bg-blue-600 text-white rounded-full flex items-center justify-center mx-auto">
        <span className="text-2xl">⏳</span>
      </div>

      <div>
        <h2 className="text-2xl font-bold text-gray-900">Conta em Análise</h2>
        <p className="text-gray-600 mt-2">
          Criação da sua conta está em análise. Em até 3 minutos você terá o resultado.
        </p>
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-xl p-4">
        <div className="text-sm text-blue-800">
          <strong>O que acontece agora?</strong>
          <ul className="mt-2 space-y-2">
            <li>✅ Análise de documentos e dados</li>
            <li>✅ Verificação de antecedentes</li>
            <li>✅ Ativação do cadastro</li>
            <li>⏳ Configuração da conta</li>
          </ul>
        </div>
      </div>

      <div className="animate-pulse text-blue-600 font-medium">
        Aguarde... Processando sua solicitação
      </div>

      <button
        onClick={() => setStep(5)}
        className="w-full bg-blue-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-blue-700 transition-colors"
      >
        Avançar para Ativação do Token
      </button>
    </div>
  );

  const renderStep5 = () => (
    <div className="space-y-6 text-center">
      <div className="w-16 h-16 bg-green-600 text-white rounded-full flex items-center justify-center mx-auto">
        <span className="text-2xl">✅</span>
      </div>

      <div>
        <h2 className="text-2xl font-bold text-gray-900">Conta Aprovada!</h2>
        <p className="text-gray-600 mt-2">
          Sua conta foi aprovada! Agora ative o Token Google para maior segurança.
        </p>
      </div>

      <div className="bg-green-50 border border-green-200 rounded-xl p-4 text-left">
        <div className="text-sm text-green-800">
          <strong>📱 Token Google Authenticator</strong>
          <p className="mt-2">
            Para realizar transferências e operações sensíveis, é <strong>obrigatório</strong> ativar o token.
          </p>
        </div>
      </div>

      <div className="space-y-4">
        <button
          onClick={() => {
            toast.success('Token ativado com sucesso!');
            navigate('/login');
          }}
          className="w-full bg-green-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-green-700 transition-colors"
        >
          ✅ Ativar Token Agora
        </button>
        
        <button
          onClick={() => {
            toast.error('Sem token ativado, você não poderá realizar transferências!');
          }}
          className="w-full bg-yellow-500 text-white py-3 px-4 rounded-xl font-medium hover:bg-yellow-600 transition-colors"
        >
          ⏰ Ativar Mais Tarde
        </button>
      </div>

      <div className="text-sm text-gray-600">
        <p>Você poderá ativar o token posteriormente nas configurações da conta.</p>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen flex items-center justify-center p-4 bg-gray-50">
      <div className="max-w-md w-full">
        <button
          onClick={() => step > 1 ? setStep(step - 1) : navigate('/login')}
          className="flex items-center gap-2 text-gray-600 hover:text-gray-800 mb-6 transition-colors"
        >
          <ArrowLeft size={20} />
          Voltar
        </button>

        <div className="bg-white rounded-2xl shadow-xl p-8">
          {step === 1 && renderStep1()}
          {step === 2 && renderStep2()}
          {step === 3 && renderStep3()}
          {step === 4 && renderStep4()}
          {step === 5 && renderStep5()}
        </div>
      </div>
    </div>
  );
};

export default CriarConta;