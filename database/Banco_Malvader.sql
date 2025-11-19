-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS banco_malvader 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_0900_ai_ci;

USE banco_malvader;

-- Tabela de Usuários
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    tipo_usuario ENUM('FUNCIONARIO', 'CLIENTE') NOT NULL,
    senha_hash VARCHAR(60) NOT NULL,
    senha_transacao VARCHAR(60),
    mfa_secret VARCHAR(255),
    mfa_enabled BOOLEAN DEFAULT FALSE,
    login_attempts INT DEFAULT 0,
    bloqueado_ate DATETIME NULL,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario_cpf (cpf),
    INDEX idx_usuario_tipo (tipo_usuario)
);

-- Tabela de Endereços
CREATE TABLE endereco (
    id_endereco INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado CHAR(2) NOT NULL,
    complemento VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    INDEX idx_endereco_cep (cep)
);

-- Tabela de Agências
CREATE TABLE agencia (
    id_agencia INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    codigo_agencia VARCHAR(10) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    data_abertura DATE DEFAULT CURRENT_DATE,
    INDEX idx_agencia_codigo (codigo_agencia)
);

-- Tabela de Endereço das Agências
CREATE TABLE endereco_agencia (
    id_endereco_agencia INT PRIMARY KEY AUTO_INCREMENT,
    id_agencia INT NOT NULL,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado CHAR(2) NOT NULL,
    complemento VARCHAR(50),
    FOREIGN KEY (id_agencia) REFERENCES agencia(id_agencia) ON DELETE CASCADE
);

-- Tabela de Clientes
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNIQUE NOT NULL,
    score_credito DECIMAL(5,2) DEFAULT 0.00,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ATIVO', 'INATIVO', 'BLOQUEADO') DEFAULT 'ATIVO',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    INDEX idx_cliente_score (score_credito)
);

-- Tabela de Funcionários
CREATE TABLE funcionario (
    id_funcionario INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNIQUE NOT NULL,
    id_agencia INT NOT NULL,
    codigo_funcionario VARCHAR(20) UNIQUE NOT NULL,
    cargo ENUM('ESTAGIARIO', 'ATENDENTE', 'GERENTE') NOT NULL,
    id_supervisor INT NULL,
    data_admissao DATE DEFAULT CURRENT_DATE,
    salario_base DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_agencia) REFERENCES agencia(id_agencia),
    FOREIGN KEY (id_supervisor) REFERENCES funcionario(id_funcionario),
    INDEX idx_funcionario_agencia (id_agencia),
    INDEX idx_funcionario_cargo (cargo)
);

-- Tabela de Contas
CREATE TABLE conta (
    id_conta INT PRIMARY KEY AUTO_INCREMENT,
    numero_conta VARCHAR(20) UNIQUE NOT NULL,
    id_agencia INT NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    tipo_conta ENUM('POUPANCA', 'CORRENTE', 'INVESTIMENTO') NOT NULL,
    id_cliente INT NOT NULL,
    data_abertura DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ATIVA', 'ENCERRADA', 'BLOQUEADA') NOT NULL DEFAULT 'ATIVA',
    FOREIGN KEY (id_agencia) REFERENCES agencia(id_agencia),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    INDEX idx_conta_numero (numero_conta),
    INDEX idx_conta_cliente (id_cliente),
    INDEX idx_conta_status (status),
    INDEX idx_conta_tipo (tipo_conta)
);

-- Tabela de Conta Poupança
CREATE TABLE conta_poupanca (
    id_conta_poupanca INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT UNIQUE NOT NULL,
    taxa_rendimento DECIMAL(5,2) NOT NULL,
    ultimo_rendimento DATETIME NULL,
    dia_aniversario INT NOT NULL DEFAULT 1,
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta) ON DELETE CASCADE
);

-- Tabela de Conta Corrente
CREATE TABLE conta_corrente (
    id_conta_corrente INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT UNIQUE NOT NULL,
    limite DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    data_vencimento DATE NOT NULL,
    taxa_manutencao DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    limite_utilizado DECIMAL(15,2) DEFAULT 0.00,
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta) ON DELETE CASCADE
);

-- Tabela de Conta Investimento
CREATE TABLE conta_investimento (
    id_conta_investimento INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT UNIQUE NOT NULL,
    perfil_risco ENUM('BAIXO', 'MEDIO', 'ALTO') NOT NULL,
    valor_minimo DECIMAL(15,2) NOT NULL,
    taxa_rendimento_base DECIMAL(5,2) NOT NULL,
    valor_aplicado DECIMAL(15,2) DEFAULT 0.00,
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta) ON DELETE CASCADE
);

-- Tabela de Transações
CREATE TABLE transacao (
    id_transacao INT PRIMARY KEY AUTO_INCREMENT,
    id_conta_origem INT NULL,
    id_conta_destino INT NULL,
    tipo_transacao ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA', 'TAXA', 'RENDIMENTO') NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao VARCHAR(100),
    status ENUM('PENDENTE', 'CONCLUIDA', 'CANCELADA') DEFAULT 'CONCLUIDA',
    FOREIGN KEY (id_conta_origem) REFERENCES conta(id_conta),
    FOREIGN KEY (id_conta_destino) REFERENCES conta(id_conta),
    INDEX idx_transacao_data (data_hora),
    INDEX idx_transacao_tipo (tipo_transacao),
    INDEX idx_transacao_origem (id_conta_origem),
    INDEX idx_transacao_destino (id_conta_destino)
);

-- Tabela de Relatórios
CREATE TABLE relatorio (
    id_relatorio INT PRIMARY KEY AUTO_INCREMENT,
    id_funcionario INT NOT NULL,
    tipo_relatorio VARCHAR(50) NOT NULL,
    data_geracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    conteudo JSON NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    INDEX idx_relatorio_data (data_geracao),
    INDEX idx_relatorio_tipo (tipo_relatorio)
);

-- Tabela de Auditoria de Abertura de Conta
CREATE TABLE auditoria_abertura_conta (
    id_auditoria INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT NOT NULL,
    id_funcionario INT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacao VARCHAR(200),
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    INDEX idx_auditoria_data (data_hora)
);

-- Tabela de Histórico de Encerramento
CREATE TABLE historico_encerramento (
    id_hist INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_funcionario INT NULL,
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    INDEX idx_historico_data (data_hora)
);

-- Tabela de Tokens de Recuperação
CREATE TABLE token_recuperacao (
    id_token INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(50) UNIQUE NOT NULL,
    id_usuario INT NOT NULL,
    data_expiracao DATETIME NOT NULL,
    utilizado BOOLEAN DEFAULT FALSE,
    data_utilizacao DATETIME NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    INDEX idx_token_validade (token, data_expiracao, utilizado)
);

-- Tabela de Logs de Segurança
CREATE TABLE log_seguranca (
    id_log INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NULL,
    tipo_evento VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    ip_origem VARCHAR(45),
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    INDEX idx_log_data (data_hora),
    INDEX idx_log_tipo (tipo_evento)
);

-- Tabela de Limites Diários
CREATE TABLE limite_diario (
    id_limite INT PRIMARY KEY AUTO_INCREMENT,
    id_conta INT NOT NULL,
    tipo_operacao ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') NOT NULL,
    data_referencia DATE NOT NULL,
    valor_utilizado DECIMAL(15,2) DEFAULT 0.00,
    limite_maximo DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (id_conta) REFERENCES conta(id_conta),
    INDEX idx_limite_data (data_referencia),
    INDEX idx_limite_conta (id_conta, tipo_operacao)
);

DELIMITER $$

-- Procedure para calcular dígito verificador (Luhn)
CREATE FUNCTION calcular_digito_verificador(numero_base VARCHAR(19)) 
RETURNS CHAR(1) DETERMINISTIC
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE soma INT DEFAULT 0;
    DECLARE digito CHAR(1);
    DECLARE num INT;
    DECLARE comprimento INT;
    
    SET comprimento = CHAR_LENGTH(numero_base);
    
    WHILE i <= comprimento DO
        SET digito = SUBSTRING(numero_base, comprimento - i + 1, 1);
        SET num = CAST(digito AS UNSIGNED);
        
        IF i % 2 = 1 THEN
            SET soma = soma + num;
        ELSE
            SET num = num * 2;
            IF num > 9 THEN
                SET num = num - 9;
            END IF;
            SET soma = soma + num;
        END IF;
        
        SET i = i + 1;
    END WHILE;
    
    RETURN CHAR((10 - (soma % 10)) % 10 + 48);
END$$

-- Function para gerar número da conta
CREATE FUNCTION gerar_numero_conta(id_ag INT, id_cli INT) 
RETURNS VARCHAR(20) DETERMINISTIC
BEGIN
    DECLARE base VARCHAR(19);
    DECLARE dv CHAR(1);
    DECLARE codigo_agencia VARCHAR(10);
    
    -- Obter código da agência
    SELECT codigo_agencia INTO codigo_agencia FROM agencia WHERE id_agencia = id_ag;
    
    -- Formatar base: AAAACCCCCSSMMDDHHMM (A=agência, C=cliente, S=sequencial, M=minuto, etc.)
    SET base = LPAD(codigo_agencia, 4, '0') 
                || LPAD(id_cli, 5, '0') 
                || LPAD(FLOOR(RAND() * 10000), 4, '0')
                || DATE_FORMAT(NOW(), '%m%d%H%i');
    
    SET dv = calcular_digito_verificador(base);
    RETURN CONCAT(base, dv);
END$$

-- Procedure para alterar senha com validação
CREATE PROCEDURE alterar_senha_usuario(
    IN p_id_usuario INT,
    IN p_senha_clara VARCHAR(255)
)
BEGIN
    DECLARE v_maiuscula TINYINT DEFAULT 0;
    DECLARE v_minuscula TINYINT DEFAULT 0;
    DECLARE v_digito TINYINT DEFAULT 0;
    DECLARE v_especial TINYINT DEFAULT 0;
    DECLARE i INT DEFAULT 1;
    DECLARE ch CHAR(1);
    DECLARE len INT;
    
    SET len = CHAR_LENGTH(p_senha_clara);
    
    -- Validar comprimento da senha
    IF len < 8 OR len > 12 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Senha deve ter entre 8 e 12 caracteres';
    END IF;
    
    -- Validar complexidade
    WHILE i <= len DO
        SET ch = SUBSTRING(p_senha_clara, i, 1);
        IF ch REGEXP '[A-Z]' THEN SET v_maiuscula = 1;
        ELSEIF ch REGEXP '[a-z]' THEN SET v_minuscula = 1;
        ELSEIF ch REGEXP '[0-9]' THEN SET v_digito = 1;
        ELSEIF ch REGEXP '[@#$%^&+=!]' THEN SET v_especial = 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    
    IF v_maiuscula = 0 OR v_minuscula = 0 OR v_digito = 0 OR v_especial = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Senha deve conter maiúsculas, minúsculas, números e caracteres especiais (@#$%^&+=!)';
    END IF;
    
    -- Permitir atualização via session variable
    SET @allow_password_update = 1;
    UPDATE usuario SET senha_hash = p_senha_clara WHERE id_usuario = p_id_usuario;
    SET @allow_password_update = NULL;
END$$

-- Procedure para calcular score de crédito
CREATE PROCEDURE calcular_score_credito(IN p_id_cliente INT)
BEGIN
    DECLARE v_total_transacoes DECIMAL(15,2);
    DECLARE v_media_transacoes DECIMAL(15,2);
    DECLARE v_dias_conta INT;
    DECLARE v_num_contas INT;
    DECLARE v_score DECIMAL(5,2);
    
    -- Calcular totais e médias de transações
    SELECT 
        COALESCE(SUM(CASE WHEN t.tipo_transacao = 'DEPOSITO' THEN t.valor ELSE 0 END), 0),
        COALESCE(AVG(CASE WHEN t.tipo_transacao = 'DEPOSITO' THEN t.valor ELSE NULL END), 0),
        COUNT(DISTINCT c.id_conta),
        DATEDIFF(CURRENT_DATE, MIN(c.data_abertura))
    INTO v_total_transacoes, v_media_transacoes, v_num_contas, v_dias_conta
    FROM cliente cl
    LEFT JOIN conta c ON cl.id_cliente = c.id_cliente
    LEFT JOIN transacao t ON c.id_conta = t.id_conta_origem OR c.id_conta = t.id_conta_destino
    WHERE cl.id_cliente = p_id_cliente;
    
    -- Calcular score (fórmula simplificada)
    SET v_score = LEAST(100.00, 
        (v_total_transacoes / 10000.00) * 40 +          -- 40% baseado no volume
        (v_media_transacoes / 1000.00) * 20 +           -- 20% baseado no ticket médio
        (v_num_contas * 5) +                            -- 20% baseado no número de contas
        (LEAST(v_dias_conta, 365) / 365.00) * 20        -- 20% baseado na antiguidade
    );
    
    UPDATE cliente SET score_credito = v_score WHERE id_cliente = p_id_cliente;
END$$

-- Procedure para encerrar conta
CREATE PROCEDURE encerrar_conta(
    IN p_id_conta INT,
    IN p_motivo VARCHAR(200),
    IN p_id_funcionario INT
)
BEGIN
    DECLARE v_saldo DECIMAL(15,2);
    DECLARE v_status VARCHAR(20);
    DECLARE v_id_cliente INT;
    
    SELECT saldo, status, id_cliente 
    INTO v_saldo, v_status, v_id_cliente
    FROM conta 
    WHERE id_conta = p_id_conta FOR UPDATE;
    
    IF v_status <> 'ATIVA' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Apenas contas ativas podem ser encerradas';
    END IF;
    
    IF v_saldo <> 0.00 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Não é possível encerrar conta com saldo diferente de zero';
    END IF;
    
    -- Atualizar status da conta
    UPDATE conta SET status = 'ENCERRADA' WHERE id_conta = p_id_conta;
    
    -- Registrar no histórico
    INSERT INTO historico_encerramento (id_conta, motivo, id_funcionario)
    VALUES (p_id_conta, p_motivo, p_id_funcionario);
    
    -- Recalcular score do cliente
    CALL calcular_score_credito(v_id_cliente);
END$$

-- Procedure para aplicar rendimento na poupança
CREATE PROCEDURE aplicar_rendimento_poupanca()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_id_conta INT;
    DECLARE v_taxa_rendimento DECIMAL(5,2);
    DECLARE v_saldo DECIMAL(15,2);
    DECLARE v_rendimento DECIMAL(15,2);
    DECLARE cur CURSOR FOR 
        SELECT cp.id_conta, cp.taxa_rendimento, c.saldo
        FROM conta_poupanca cp
        JOIN conta c ON cp.id_conta = c.id_conta
        WHERE c.status = 'ATIVA' 
        AND DAY(CURRENT_DATE) = cp.dia_aniversario
        AND (cp.ultimo_rendimento IS NULL OR DATE(cp.ultimo_rendimento) < CURRENT_DATE);
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    
    read_loop: LOOP
        FETCH cur INTO v_id_conta, v_taxa_rendimento, v_saldo;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Calcular rendimento
        SET v_rendimento = v_saldo * (v_taxa_rendimento / 100);
        
        -- Atualizar saldo
        UPDATE conta SET saldo = saldo + v_rendimento WHERE id_conta = v_id_conta;
        
        -- Registrar transação de rendimento
        INSERT INTO transacao (id_conta_destino, tipo_transacao, valor, descricao)
        VALUES (v_id_conta, 'RENDIMENTO', v_rendimento, CONCAT('Rendimento poupança - Taxa: ', v_taxa_rendimento, '%'));
        
        -- Atualizar data do último rendimento
        UPDATE conta_poupanca SET ultimo_rendimento = CURRENT_TIMESTAMP WHERE id_conta = v_id_conta;
    END LOOP;
    
    CLOSE cur;
END$$

-- Procedure para gerar relatório de movimentações
CREATE PROCEDURE gerar_relatorio_movimentacoes(
    IN p_data_inicio DATE,
    IN p_data_fim DATE,
    IN p_id_agencia INT,
    IN p_id_funcionario INT
)
BEGIN
    DECLARE relatorio_json JSON;
    
    SELECT JSON_OBJECT(
        'periodo', JSON_OBJECT('inicio', p_data_inicio, 'fim', p_data_fim),
        'agencia', p_id_agencia,
        'movimentacoes', JSON_ARRAYAGG(
            JSON_OBJECT(
                'data', DATE(t.data_hora),
                'tipo', t.tipo_transacao,
                'valor', t.valor,
                'conta_origem', co.numero_conta,
                'conta_destino', cd.numero_conta,
                'cliente', u.nome
            )
        ),
        'totais', JSON_OBJECT(
            'depositos', COALESCE(SUM(CASE WHEN t.tipo_transacao = 'DEPOSITO' THEN t.valor ELSE 0 END), 0),
            'saques', COALESCE(SUM(CASE WHEN t.tipo_transacao = 'SAQUE' THEN t.valor ELSE 0 END), 0),
            'transferencias', COALESCE(SUM(CASE WHEN t.tipo_transacao = 'TRANSFERENCIA' THEN t.valor ELSE 0 END), 0),
            'total_geral', COALESCE(SUM(t.valor), 0)
        )
    ) INTO relatorio_json
    FROM transacao t
    LEFT JOIN conta co ON t.id_conta_origem = co.id_conta
    LEFT JOIN conta cd ON t.id_conta_destino = cd.id_conta
    LEFT JOIN cliente cl ON co.id_cliente = cl.id_cliente OR cd.id_cliente = cl.id_cliente
    LEFT JOIN usuario u ON cl.id_usuario = u.id_usuario
    WHERE DATE(t.data_hora) BETWEEN p_data_inicio AND p_data_fim
    AND (p_id_agencia IS NULL OR co.id_agencia = p_id_agencia OR cd.id_agencia = p_id_agencia);
    
    INSERT INTO relatorio (id_funcionario, tipo_relatorio, conteudo)
    VALUES (p_id_funcionario, 'MOVIMENTACOES', relatorio_json);
END$$

DELIMITER ;

DELIMITER $$

-- Trigger para gerar número da conta automaticamente
CREATE TRIGGER conta_before_insert 
BEFORE INSERT ON conta 
FOR EACH ROW
BEGIN
    IF NEW.numero_conta IS NULL OR NEW.numero_conta = '' THEN
        SET NEW.numero_conta = gerar_numero_conta(NEW.id_agencia, NEW.id_cliente);
    END IF;
END$$

-- Trigger para atualizar saldo após transações
CREATE TRIGGER atualizar_saldo_after_insert 
AFTER INSERT ON transacao 
FOR EACH ROW
BEGIN
    IF NEW.status = 'CONCLUIDA' THEN
        CASE NEW.tipo_transacao
            WHEN 'DEPOSITO' THEN
                UPDATE conta SET saldo = saldo + NEW.valor WHERE id_conta = NEW.id_conta_destino;
            WHEN 'SAQUE' THEN
                UPDATE conta SET saldo = saldo - NEW.valor WHERE id_conta = NEW.id_conta_origem;
            WHEN 'TRANSFERENCIA' THEN
                UPDATE conta SET saldo = saldo - NEW.valor WHERE id_conta = NEW.id_conta_origem;
                UPDATE conta SET saldo = saldo + NEW.valor WHERE id_conta = NEW.id_conta_destino;
            WHEN 'TAXA' THEN
                UPDATE conta SET saldo = saldo - NEW.valor WHERE id_conta = NEW.id_conta_origem;
            WHEN 'RENDIMENTO' THEN
                UPDATE conta SET saldo = saldo + NEW.valor WHERE id_conta = NEW.id_conta_destino;
        END CASE;
    END IF;
END$$

-- Trigger para validar limites diários
CREATE TRIGGER validar_limite_diario 
BEFORE INSERT ON transacao 
FOR EACH ROW
BEGIN
    DECLARE v_total_hoje DECIMAL(15,2);
    DECLARE v_limite_maximo DECIMAL(15,2);
    DECLARE v_id_conta INT;
    
    IF NEW.tipo_transacao IN ('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') THEN
        -- Determinar conta principal para o limite
        IF NEW.tipo_transacao = 'DEPOSITO' THEN
            SET v_id_conta = NEW.id_conta_destino;
        ELSE
            SET v_id_conta = NEW.id_conta_origem;
        END IF;
        
        -- Definir limites máximos
        CASE NEW.tipo_transacao
            WHEN 'DEPOSITO' THEN SET v_limite_maximo = 10000.00;
            WHEN 'SAQUE' THEN SET v_limite_maximo = 5000.00;
            WHEN 'TRANSFERENCIA' THEN SET v_limite_maximo = 8000.00;
        END CASE;
        
        -- Calcular total utilizado hoje
        SELECT COALESCE(SUM(valor), 0) INTO v_total_hoje
        FROM transacao
        WHERE ((tipo_transacao = 'DEPOSITO' AND id_conta_destino = v_id_conta) OR
               (tipo_transacao IN ('SAQUE', 'TRANSFERENCIA') AND id_conta_origem = v_id_conta))
        AND DATE(data_hora) = CURRENT_DATE
        AND status = 'CONCLUIDA';
        
        -- Verificar se excede o limite
        IF (v_total_hoje + NEW.valor) > v_limite_maximo THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Limite diário excedido para esta operação';
        END IF;
    END IF;
END$$

-- Trigger para auditoria de abertura de conta
CREATE TRIGGER auditoria_abertura_conta 
AFTER INSERT ON conta 
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_abertura_conta (id_conta, id_funcionario, observacao)
    VALUES (NEW.id_conta, @current_funcionario_id, CONCAT('Abertura de conta ', NEW.tipo_conta));
END$$

-- Trigger para validar senha forte
CREATE TRIGGER validar_senha_forte 
BEFORE UPDATE ON usuario 
FOR EACH ROW
BEGIN
    -- Apenas validar se a senha está sendo alterada e não é via procedure
    IF NEW.senha_hash <> OLD.senha_hash AND @allow_password_update IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Senha deve ser alterada através do procedimento alterar_senha_usuario';
    END IF;
END$$

-- Trigger para log de segurança
CREATE TRIGGER log_tentativa_login 
AFTER UPDATE ON usuario 
FOR EACH ROW
BEGIN
    IF NEW.login_attempts <> OLD.login_attempts THEN
        INSERT INTO log_seguranca (id_usuario, tipo_evento, descricao)
        VALUES (NEW.id_usuario, 'TENTATIVA_LOGIN', 
                CONCAT('Tentativa de login - Tentativas: ', NEW.login_attempts));
    END IF;
    
    IF NEW.bloqueado_ate IS NOT NULL AND OLD.bloqueado_ate IS NULL THEN
        INSERT INTO log_seguranca (id_usuario, tipo_evento, descricao)
        VALUES (NEW.id_usuario, 'BLOQUEIO_CONTA', 'Conta bloqueada devido a múltiplas tentativas falhas');
    END IF;
END$$

DELIMITER ;

-- View para resumo de contas por cliente
CREATE OR REPLACE VIEW vw_resumo_contas_cliente AS
SELECT 
    c.id_cliente,
    u.nome,
    u.cpf,
    COUNT(co.id_conta) AS total_contas,
    SUM(CASE WHEN co.status = 'ATIVA' THEN 1 ELSE 0 END) AS contas_ativas,
    SUM(co.saldo) AS saldo_total,
    c.score_credito
FROM cliente c
JOIN usuario u ON c.id_usuario = u.id_usuario
LEFT JOIN conta co ON c.id_cliente = co.id_cliente
GROUP BY c.id_cliente, u.nome, u.cpf, c.score_credito;

-- View para movimentações recentes
CREATE OR REPLACE VIEW vw_movimentacoes_recentes AS
SELECT 
    t.id_transacao,
    t.tipo_transacao,
    t.valor,
    t.data_hora,
    t.descricao,
    co.numero_conta AS conta_origem,
    cd.numero_conta AS conta_destino,
    uo.nome AS cliente_origem,
    ud.nome AS cliente_destino
FROM transacao t
LEFT JOIN conta co ON t.id_conta_origem = co.id_conta
LEFT JOIN conta cd ON t.id_conta_destino = cd.id_conta
LEFT JOIN cliente clo ON co.id_cliente = clo.id_cliente
LEFT JOIN cliente cld ON cd.id_cliente = cld.id_cliente
LEFT JOIN usuario uo ON clo.id_usuario = uo.id_usuario
LEFT JOIN usuario ud ON cld.id_usuario = ud.nome
WHERE t.data_hora >= NOW() - INTERVAL 90 DAY
ORDER BY t.data_hora DESC;

-- View para contas com detalhes
CREATE OR REPLACE VIEW vw_contas_detalhadas AS
SELECT 
    c.id_conta,
    c.numero_conta,
    c.tipo_conta,
    c.saldo,
    c.status,
    c.data_abertura,
    u.nome AS cliente_nome,
    u.cpf AS cliente_cpf,
    a.nome AS agencia_nome,
    a.codigo_agencia,
    cp.taxa_rendimento AS taxa_poupanca,
    cc.limite AS limite_corrente,
    cc.data_vencimento AS vencimento_corrente,
    ci.perfil_risco,
    ci.valor_minimo AS investimento_minimo
FROM conta c
JOIN cliente cl ON c.id_cliente = cl.id_cliente
JOIN usuario u ON cl.id_usuario = u.id_usuario
JOIN agencia a ON c.id_agencia = a.id_agencia
LEFT JOIN conta_poupanca cp ON c.id_conta = cp.id_conta
LEFT JOIN conta_corrente cc ON c.id_conta = cc.id_conta
LEFT JOIN conta_investimento ci ON c.id_conta = ci.id_conta;

-- View para funcionários com detalhes
CREATE OR REPLACE VIEW vw_funcionarios_detalhados AS
SELECT 
    f.id_funcionario,
    f.codigo_funcionario,
    f.cargo,
    f.data_admissao,
    u.nome,
    u.cpf,
    u.telefone,
    a.nome AS agencia_nome,
    sup.nome AS supervisor_nome
FROM funcionario f
JOIN usuario u ON f.id_usuario = u.id_usuario
JOIN agencia a ON f.id_agencia = a.id_agencia
LEFT JOIN funcionario fs ON f.id_supervisor = fs.id_funcionario
LEFT JOIN usuario sup ON fs.id_usuario = sup.id_usuario;

-- View para clientes inadimplentes
CREATE OR REPLACE VIEW vw_clientes_inadimplentes AS
SELECT 
    c.id_cliente,
    u.nome,
    u.cpf,
    c.score_credito,
    SUM(co.saldo) AS saldo_total,
    COUNT(co.id_conta) AS total_contas,
    MIN(co.data_abertura) AS data_primeira_conta
FROM cliente c
JOIN usuario u ON c.id_usuario = u.id_usuario
JOIN conta co ON c.id_cliente = co.id_cliente
WHERE co.status = 'ATIVA'
AND (co.saldo < 0 OR EXISTS (
    SELECT 1 FROM conta_corrente cc 
    WHERE cc.id_conta = co.id_conta 
    AND co.saldo + cc.limite < 0
))
GROUP BY c.id_cliente, u.nome, u.cpf, c.score_credito;

--- ÍNDICES DE PERFORMANCE 
CREATE INDEX idx_transacao_conta_data ON transacao(id_conta_origem, id_conta_destino, data_hora);
CREATE INDEX idx_conta_agencia_status ON conta(id_agencia, status);
CREATE INDEX idx_cliente_usuario ON cliente(id_usuario);
CREATE INDEX idx_funcionario_usuario ON funcionario(id_usuario);
CREATE INDEX idx_usuario_bloqueado ON usuario(bloqueado_ate, login_attempts);
CREATE INDEX idx_transacao_valor_data ON transacao(valor, data_hora);
CREATE INDEX idx_conta_saldo_tipo ON conta(saldo, tipo_conta);


-- Inserir agências
INSERT INTO agencia (nome, codigo_agencia, telefone) VALUES
('Agência Centro', '001', '(61) 3321-1000'),
('Agência Norte', '002', '(61) 3321-2000'),
('Agência Sul', '003', '(61) 3321-3000');

-- Inserir endereços das agências
INSERT INTO endereco_agencia (id_agencia, cep, logradouro, numero, bairro, cidade, estado) VALUES
(1, '70000-000', 'Praça Central', 100, 'Centro', 'Brasília', 'DF'),
(2, '70000-001', 'Quadra Norte', 200, 'Asa Norte', 'Brasília', 'DF'),
(3, '70000-002', 'Quadra Sul', 300, 'Asa Sul', 'Brasília', 'DF');

-- Inserir usuários (senhas: Senha123! para todos, senha transação: 123456)
INSERT INTO usuario (nome, cpf, data_nascimento, telefone, tipo_usuario, senha_hash, senha_transacao) VALUES
-- Clientes
('João Silva', '12345678901', '1980-01-15', '(61) 99999-9999', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
('Maria Santos', '23456789012', '1985-05-20', '(61) 88888-8888', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
('Pedro Oliveira', '34567890123', '1990-08-10', '(61) 77777-7777', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
('Ana Costa', '45678901234', '1975-12-05', '(61) 66666-6666', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
('Carlos Lima', '56789012345', '1988-03-25', '(61) 55555-5555', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
('Fernanda Rocha', '67890123456', '1992-07-18', '(61) 44444-4444', 'CLIENTE', '$2a$10$ABC123...', '$2a$10$XYZ789...'),
-- Funcionários
('Gerente Principal', '11111111111', '1970-01-01', '(61) 33333-3333', 'FUNCIONARIO', '$2a$10$ABC123...', NULL),
('Atendente Secundário', '22222222222', '1985-06-15', '(61) 22222-2222', 'FUNCIONARIO', '$2a$10$ABC123...', NULL),
('Estagiário Operacional', '33333333333', '1995-03-10', '(61) 11111-1111', 'FUNCIONARIO', '$2a$10$ABC123...', NULL);

-- Inserir endereços dos usuários
INSERT INTO endereco (id_usuario, cep, logradouro, numero, bairro, cidade, estado, complemento) VALUES
(1, '70000-100', 'Rua das Flores', 123, 'Asa Sul', 'Brasília', 'DF', 'Apto 101'),
(2, '70000-101', 'Quadra 302', 456, 'Asa Norte', 'Brasília', 'DF', 'Casa 2'),
(3, '70000-102', 'Rua das Palmeiras', 789, 'Lago Sul', 'Brasília', 'DF', NULL),
(4, '70000-103', 'Quadra 105', 321, 'Asa Sul', 'Brasília', 'DF', 'Bloco C'),
(5, '70000-104', 'Rua dos Ipês', 654, 'Lago Norte', 'Brasília', 'DF', 'Apto 302'),
(6, '70000-105', 'Quadra 405', 987, 'Asa Norte', 'Brasília', 'DF', 'Casa 5'),
(7, '70000-106', 'Setor Commercial', 111, 'Centro', 'Brasília', 'DF', 'Sala 201'),
(8, '70000-107', 'Setor Commercial', 222, 'Centro', 'Brasília', 'DF', 'Sala 102'),
(9, '70000-108', 'Setor Commercial', 333, 'Centro', 'Brasília', 'DF', 'Sala 103');

-- Inserir clientes
INSERT INTO cliente (id_usuario, score_credito) VALUES
(1, 85.5), (2, 92.0), (3, 78.5), (4, 95.0), (5, 88.0), (6, 90.5);

-- Inserir funcionários
INSERT INTO funcionario (id_usuario, id_agencia, codigo_funcionario, cargo, id_supervisor, salario_base) VALUES
(7, 1, 'GER001', 'GERENTE', NULL, 8000.00),
(8, 1, 'ATD001', 'ATENDENTE', 1, 3000.00),
(9, 1, 'EST001', 'ESTAGIARIO', 1, 1500.00);

-- Inserir contas
INSERT INTO conta (numero_conta, id_agencia, saldo, tipo_conta, id_cliente, data_abertura) VALUES
-- Contas do João Silva
(NULL, 1, 5000.00, 'CORRENTE', 1, '2023-01-15'),
(NULL, 1, 10000.00, 'POUPANCA', 1, '2023-02-20'),
-- Contas da Maria Santos
(NULL, 1, 7500.00, 'CORRENTE', 2, '2023-03-10'),
(NULL, 1, 15000.00, 'INVESTIMENTO', 2, '2023-04-05'),
-- Contas do Pedro Oliveira
(NULL, 2, 3000.00, 'CORRENTE', 3, '2023-05-12'),
-- Contas da Ana Costa
(NULL, 2, 12000.00, 'POUPANCA', 4, '2023-06-18'),
-- Contas do Carlos Lima
(NULL, 3, 6000.00, 'CORRENTE', 5, '2023-07-22'),
(NULL, 3, 8000.00, 'INVESTIMENTO', 5, '2023-08-30'),
-- Contas da Fernanda Rocha
(NULL, 3, 9000.00, 'POUPANCA', 6, '2023-09-25');

-- Inserir detalhes das contas poupança
INSERT INTO conta_poupanca (id_conta, taxa_rendimento, dia_aniversario) VALUES
(2, 0.5, 20),  -- João Silva
(6, 0.5, 18),  -- Ana Costa
(9, 0.5, 25);  -- Fernanda Rocha

-- Inserir detalhes das contas corrente
INSERT INTO conta_corrente (id_conta, limite, data_vencimento, taxa_manutencao) VALUES
(1, 2000.00, '2024-12-31', 15.00),  -- João Silva
(3, 1500.00, '2024-12-31', 12.00),  -- Maria Santos
(5, 1000.00, '2024-12-31', 10.00),  -- Pedro Oliveira
(7, 2500.00, '2024-12-31', 18.00);  -- Carlos Lima

-- Inserir detalhes das contas investimento
INSERT INTO conta_investimento (id_conta, perfil_risco, valor_minimo, taxa_rendimento_base, valor_aplicado) VALUES
(4, 'MEDIO', 5000.00, 1.2, 15000.00),  -- Maria Santos
(8, 'ALTO', 10000.00, 2.5, 8000.00);   -- Carlos Lima

-- Inserir algumas transações de exemplo
INSERT INTO transacao (id_conta_origem, id_conta_destino, tipo_transacao, valor, descricao) VALUES
-- Depósitos
(NULL, 1, 'DEPOSITO', 1000.00, 'Depósito inicial'),
(NULL, 2, 'DEPOSITO', 5000.00, 'Depósito poupança'),
(NULL, 3, 'DEPOSITO', 2000.00, 'Depósito salário'),
-- Saques
(1, NULL, 'SAQUE', 500.00, 'Saque caixa eletrônico'),
(3, NULL, 'SAQUE', 300.00, 'Saque agência'),
-- Transferências
(1, 3, 'TRANSFERENCIA', 200.00, 'Transferência para Maria'),
(3, 5, 'TRANSFERENCIA', 150.00, 'Pagamento serviços');

-- Atualizar scores de crédito
CALL calcular_score_credito(1);
CALL calcular_score_credito(2);
CALL calcular_score_credito(3);
CALL calcular_score_credito(4);
CALL calcular_score_credito(5);
CALL calcular_score_credito(6);

