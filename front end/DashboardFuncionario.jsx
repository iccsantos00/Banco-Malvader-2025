// src/pages/Funcionario/DashboardFuncionario.jsx - VERSÃO COMPLETA
import React, { useState, useEffect } from 'react';
import { useAuth } from '../../contexts/AuthContext';
import {
  BarChart3, Users, CreditCard, ArrowUpDown,
  FileText, Settings, Shield, AlertTriangle,
  LogOut, Bell, PieChart, TrendingUp,
  UserCheck, UserX, Clock, Download,
  Search, Filter, Eye, Edit, Plus,
  Trash2, Key, Activity, Zap,
  DollarSign, TrendingDown, ArrowRight
} from 'lucide-react';
import toast from 'react-hot-toast';

const DashboardFuncionario = () => {
  const { user, logout } = useAuth();
  const [abaAtiva, setAbaAtiva] = useState('dashboard');
  const [horaAtual, setHoraAtual] = useState('');
  const [modalAberto, setModalAberto] = useState(null);

  useEffect(() => {
    const atualizarHora = () => {
      const agora = new Date();
      setHoraAtual(agora.toLocaleString('pt-BR', {
        timeZone: 'America/Sao_Paulo',
        weekday: 'long',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      }));
    };
    
    atualizarHora();
    const interval = setInterval(atualizarHora, 1000);
    return () => clearInterval(interval);
  }, []);

  // Dados para métricas
  const metricas = [
    { icone: Users, label: 'CLIENTES', valor: '1.247', variacao: '+12%', cor: 'blue' },
    { icone: CreditCard, label: 'CONTAS', valor: '2.893', variacao: '+8%', cor: 'green' },
    { icone: ArrowUpDown, label: 'TRANSF.', valor: 'R$ 1.2M', variacao: '+15%', cor: 'purple' },
    { icone: AlertTriangle, label: 'PENDÊNCIAS', valor: '23', variacao: '-5%', cor: 'red' }
  ];

  // Menu de Operações Rápidas
  const operacoesRapidas = [
    {
      icone: Plus,
      label: 'ABRIR CONTA',
      descricao: 'Criar nova conta (CP/CC/CI)',
      acao: () => setModalAberto('abrir-conta'),
      cor: 'green'
    },
    {
      icone: UserX,
      label: 'ENCERRAR CONTA',
      descricao: 'Encerrar conta com senha',
      acao: () => setModalAberto('encerrar-conta'),
      cor: 'red'
    },
    {
      icone: Search,
      label: 'CONSULTAR DADOS',
      descricao: 'Consultar informações',
      acao: () => setModalAberto('consultar-dados'),
      cor: 'blue'
    },
    {
      icone: Edit,
      label: 'ALTERAR DADOS',
      descricao: 'Alterar dados de clientes',
      acao: () => setModalAberto('alterar-dados'),
      cor: 'yellow'
    }
  ];

  // Dados para gráfico de investimentos
  const dadosInvestimentos = [
    { tipo: 'CDB', valor: 1890000, porcentagem: 45, cor: 'bg-blue-500' },
    { tipo: 'Tesouro Direto', valor: 1250000, porcentagem: 30, cor: 'bg-green-500' },
    { tipo: 'Fundos Imobiliários', valor: 680000, porcentagem: 16, cor: 'bg-purple-500' },
    { tipo: 'Ações', valor: 380000, porcentagem: 9, cor: 'bg-orange-500' }
  ];

  // Logs de Auditoria
  const logsAuditoria = [
    { 
      usuario: 'João Silva', 
      acao: 'Login realizado', 
      horario: '14:30:15', 
      status: 'sucesso',
      ip: '192.168.1.100',
      dispositivo: 'Chrome Windows'
    },
    { 
      usuario: 'Maria Santos', 
      acao: 'Tentativa de login falhou', 
      horario: '14:25:10', 
      status: 'erro',
      ip: '177.220.180.15',
      dispositivo: 'Mobile Android'
    },
    { 
      usuario: 'Carlos Oliveira', 
      acao: 'Transferência R$ 1.500,00', 
      horario: '14:20:45', 
      status: 'sucesso',
      ip: '201.30.180.45',
      dispositivo: 'Safari macOS'
    },
    { 
      usuario: 'Ana Costa', 
      acao: 'Alteração de senha', 
      horario: '14:15:30', 
      status: 'sucesso',
      ip: '189.50.200.33',
      dispositivo: 'Firefox Linux'
    }
  ];

  // Incidentes de Fraude
  const incidentesFraude = [
    {
      tipo: 'Múltiplas Tentativas',
      descricao: '5 tentativas de login em 2 minutos',
      severidade: 'alta',
      cpf: '123.456.789-00',
      horario: '14:28:00'
    },
    {
      tipo: 'Transação Suspeita',
      descricao: 'Transferência acima do padrão',
      severidade: 'media',
      cpf: '987.654.321-00',
      horario: '13:45:30'
    }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center">
              <div className="bg-blue-600 text-white p-2 rounded-lg">
                <BarChart3 size={24} />
              </div>
              <div className="ml-3">
                <h1 className="text-xl font-bold text-gray-900">Banco Malvader</h1>
                <p className="text-sm text-gray-600">Painel do Funcionário</p>
              </div>
            </div>
            
            <div className="flex items-center space-x-6">
              <div className="text-right">
                <p className="text-sm font-medium text-gray-900">{user?.nome} ({user?.cargo})</p>
                <p className="text-sm text-gray-600">{horaAtual} • Brasília/DF</p>
              </div>
              
              <div className="flex items-center space-x-2">
                <button className="p-2 text-gray-600 hover:text-gray-800 hover:bg-gray-100 rounded-lg transition-colors">
                  <Bell size={20} />
                </button>
                <button
                  onClick={logout}
                  className="flex items-center gap-2 px-4 py-2 text-gray-600 hover:text-gray-800 hover:bg-gray-100 rounded-lg transition-colors"
                >
                  <LogOut size={20} />
                  Sair
                </button>
              </div>
            </div>
          </div>
        </div>
      </header>

      {/* Navegação */}
      <nav className="bg-white border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex space-x-8">
            {[
              { id: 'dashboard', label: 'DASHBOARD', icone: BarChart3 },
              { id: 'clientes', label: 'CLIENTES', icone: Users },
              { id: 'contas', label: 'CONTAS', icone: CreditCard },
              { id: 'investimentos', label: 'INVESTIMENTOS', icone: TrendingUp },
              { id: 'relatorios', label: 'RELATÓRIOS', icone: FileText },
              { id: 'auditoria', label: 'AUDITORIA', icone: Shield },
              { id: 'config', label: 'CONFIGURAÇÕES', icone: Settings }
            ].map(aba => (
              <button
                key={aba.id}
                onClick={() => setAbaAtiva(aba.id)}
                className={`flex items-center gap-2 py-4 px-2 border-b-2 font-medium text-sm transition-colors ${
                  abaAtiva === aba.id
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                <aba.icone size={18} />
                {aba.label}
              </button>
            ))}
          </div>
        </div>
      </nav>

      {/* Conteúdo Principal */}
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          
          {/* DASHBOARD PRINCIPAL */}
          {abaAtiva === 'dashboard' && (
            <div className="space-y-6">
              {/* Métricas */}
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {metricas.map((metrica, index) => (
                  <div key={index} className="bg-white rounded-xl shadow-sm p-6">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="text-sm font-medium text-gray-600">{metrica.label}</p>
                        <p className="text-2xl font-bold text-gray-900 mt-1">{metrica.valor}</p>
                        <p className={`text-sm font-medium ${
                          metrica.variacao.startsWith('+') ? 'text-green-600' : 'text-red-600'
                        }`}>
                          {metrica.variacao}
                        </p>
                      </div>
                      <div className={`p-3 rounded-lg ${
                        metrica.cor === 'blue' ? 'bg-blue-100' :
                        metrica.cor === 'green' ? 'bg-green-100' :
                        metrica.cor === 'purple' ? 'bg-purple-100' : 'bg-red-100'
                      }`}>
                        <metrica.icone className={`${
                          metrica.cor === 'blue' ? 'text-blue-600' :
                          metrica.cor === 'green' ? 'text-green-600' :
                          metrica.cor === 'purple' ? 'text-purple-600' : 'text-red-600'
                        }`} size={24} />
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* Operações Rápidas */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">Operações Rápidas</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                  {operacoesRapidas.map((op, index) => (
                    <button
                      key={index}
                      onClick={op.acao}
                      className="flex items-center gap-4 p-4 border border-gray-200 rounded-lg hover:border-blue-300 hover:bg-blue-50 transition-colors group"
                    >
                      <div className={`p-3 rounded-lg ${
                        op.cor === 'green' ? 'bg-green-100 group-hover:bg-green-200' :
                        op.cor === 'red' ? 'bg-red-100 group-hover:bg-red-200' :
                        op.cor === 'blue' ? 'bg-blue-100 group-hover:bg-blue-200' :
                        'bg-yellow-100 group-hover:bg-yellow-200'
                      } transition-colors`}>
                        <op.icone className={`${
                          op.cor === 'green' ? 'text-green-600' :
                          op.cor === 'red' ? 'text-red-600' :
                          op.cor === 'blue' ? 'text-blue-600' : 'text-yellow-600'
                        }`} size={24} />
                      </div>
                      <div className="text-left">
                        <p className="font-semibold text-gray-900">{op.label}</p>
                        <p className="text-sm text-gray-600">{op.descricao}</p>
                      </div>
                    </button>
                  ))}
                </div>
              </div>

              {/* Gráficos e Pendências */}
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                {/* Movimentação Financeira */}
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Movimentação Financeira</h3>
                  <div className="space-y-4">
                    {[
                      { label: 'Depósitos', porcentagem: 65, valor: 'R$ 2.1M', cor: 'bg-blue-500' },
                      { label: 'Transferências', porcentagem: 35, valor: 'R$ 1.1M', cor: 'bg-green-500' },
                      { label: 'Saques', porcentagem: 22, valor: 'R$ 720K', cor: 'bg-yellow-500' },
                      { label: 'Pagamentos', porcentagem: 25, valor: 'R$ 820K', cor: 'bg-purple-500' }
                    ].map((item, index) => (
                      <div key={index} className="flex justify-between items-center">
                        <span className="text-sm text-gray-600">{item.label}</span>
                        <div className="flex items-center gap-3">
                          <div className="w-32 bg-gray-200 rounded-full h-2">
                            <div className={`${item.cor} h-2 rounded-full`} style={{ width: `${item.porcentagem}%` }}></div>
                          </div>
                          <span className="text-sm font-medium text-gray-900">{item.valor}</span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Contas Pendentes */}
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Contas Pendentes</h3>
                  <div className="space-y-4">
                    {[
                      { cliente: 'Carlos Oliveira', tipo: 'CORRENTE', cpf: '456.789.123-00', status: 'ANÁLISE' },
                      { cliente: 'Ana Costa', tipo: 'POUPANÇA', cpf: '789.123.456-00', status: 'DOCUMENTAÇÃO' },
                      { cliente: 'Roberto Silva', tipo: 'INVESTIMENTO', cpf: '321.654.987-00', status: 'APROVAÇÃO' }
                    ].map((conta, index) => (
                      <div key={index} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                        <div>
                          <p className="font-medium text-gray-900">{conta.cliente}</p>
                          <p className="text-sm text-gray-600">{conta.tipo} • {conta.cpf}</p>
                        </div>
                        <div className="flex items-center gap-2">
                          <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                            conta.status === 'ANÁLISE' ? 'bg-yellow-100 text-yellow-800' :
                            conta.status === 'DOCUMENTAÇÃO' ? 'bg-blue-100 text-blue-800' :
                            'bg-green-100 text-green-800'
                          }`}>
                            {conta.status}
                          </span>
                          <button className="p-1 text-gray-400 hover:text-gray-600">
                            <Eye size={16} />
                          </button>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              {/* Atividades Recentes */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">Atividades Recentes</h3>
                <div className="space-y-3">
                  {[
                    { acao: 'Conta aprovada', cliente: 'João Silva', tempo: '5 min atrás', tipo: 'sucesso' },
                    { acao: 'Documentação pendente', cliente: 'Maria Santos', tempo: '15 min atrás', tipo: 'alerta' },
                    { acao: 'Transferência realizada', cliente: 'Carlos Oliveira', tempo: '30 min atrás', tipo: 'sucesso' },
                    { acao: 'Cadastro recusado', cliente: 'Ana Costa', tempo: '1 hora atrás', tipo: 'erro' }
                  ].map((atividade, index) => (
                    <div key={index} className="flex items-center gap-4 p-3 hover:bg-gray-50 rounded-lg">
                      <div className={`w-2 h-2 rounded-full ${
                        atividade.tipo === 'sucesso' ? 'bg-green-500' :
                        atividade.tipo === 'alerta' ? 'bg-yellow-500' : 'bg-red-500'
                      }`}></div>
                      <div className="flex-1">
                        <p className="text-sm font-medium text-gray-900">{atividade.acao}</p>
                        <p className="text-sm text-gray-600">{atividade.cliente}</p>
                      </div>
                      <span className="text-sm text-gray-500">{atividade.tempo}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          )}

          {/* DASHBOARD DE INVESTIMENTOS */}
          {abaAtiva === 'investimentos' && (
            <div className="bg-white rounded-xl shadow-sm p-6">
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-bold text-gray-900">Dashboard de Investimentos</h2>
                <button className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors">
                  <Download size={20} />
                  Exportar Relatório
                </button>
              </div>

              <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
                {/* Métricas de Investimento */}
                <div className="bg-gradient-to-br from-blue-500 to-blue-600 text-white rounded-xl p-6">
                  <p className="text-sm opacity-90">Total Investido</p>
                  <p className="text-3xl font-bold mt-2">R$ 4.2M</p>
                  <p className="text-sm opacity-90 mt-1">+15% este mês</p>
                </div>
                <div className="bg-gradient-to-br from-green-500 to-green-600 text-white rounded-xl p-6">
                  <p className="text-sm opacity-90">Rendimento Mensal</p>
                  <p className="text-3xl font-bold mt-2">R$ 245K</p>
                  <p className="text-sm opacity-90 mt-1">+5.8% em relação ao mês anterior</p>
                </div>
                <div className="bg-gradient-to-br from-purple-500 to-purple-600 text-white rounded-xl p-6">
                  <p className="text-sm opacity-90">Clientes Investindo</p>
                  <p className="text-3xl font-bold mt-2">892</p>
                  <p className="text-sm opacity-90 mt-1">+12% novos investidores</p>
                </div>
              </div>

              {/* Gráfico de Distribuição */}
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <div className="bg-gray-50 rounded-xl p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Distribuição por Tipo</h3>
                  <div className="space-y-4">
                    {dadosInvestimentos.map((investimento, index) => (
                      <div key={index} className="flex items-center justify-between">
                        <div className="flex items-center gap-3">
                          <div className={`w-4 h-4 rounded ${investimento.cor}`}></div>
                          <span className="text-sm font-medium text-gray-900">{investimento.tipo}</span>
                        </div>
                        <div className="flex items-center gap-4">
                          <div className="w-32 bg-gray-200 rounded-full h-2">
                            <div className={`${investimento.cor} h-2 rounded-full`} style={{ width: `${investimento.porcentagem}%` }}></div>
                          </div>
                          <span className="text-sm font-medium text-gray-900">
                            R$ {(investimento.valor / 1000).toFixed(0)}K
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Performance Mensal */}
                <div className="bg-gray-50 rounded-xl p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Performance Mensal</h3>
                  <div className="space-y-3">
                    {[
                      { mes: 'Jan', valor: 120, crescimento: 5 },
                      { mes: 'Fev', valor: 135, crescimento: 12 },
                      { mes: 'Mar', valor: 158, crescimento: 17 },
                      { mes: 'Abr', valor: 142, crescimento: -10 },
                      { mes: 'Mai', valor: 168, crescimento: 18 },
                      { mes: 'Jun', valor: 189, crescimento: 12 }
                    ].map((mes, index) => (
                      <div key={index} className="flex items-center justify-between">
                        <span className="text-sm text-gray-600">{mes.mes}</span>
                        <div className="flex items-center gap-4">
                          <div className="w-24 bg-gray-200 rounded-full h-2">
                            <div className="bg-green-500 h-2 rounded-full" style={{ width: `${mes.valor / 2}%` }}></div>
                          </div>
                          <span className={`text-sm font-medium ${
                            mes.crescimento >= 0 ? 'text-green-600' : 'text-red-600'
                          }`}>
                            {mes.crescimento >= 0 ? '+' : ''}{mes.crescimento}%
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* DASHBOARD DE AUDITORIA */}
          {abaAtiva === 'auditoria' && (
            <div className="space-y-6">
              {/* Métricas de Segurança */}
              <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">Tentativas Suspeitas</p>
                      <p className="text-2xl font-bold text-gray-900 mt-1">12</p>
                    </div>
                    <div className="p-3 rounded-lg bg-red-100">
                      <AlertTriangle className="text-red-600" size={24} />
                    </div>
                  </div>
                </div>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">Incidentes Hoje</p>
                      <p className="text-2xl font-bold text-gray-900 mt-1">3</p>
                    </div>
                    <div className="p-3 rounded-lg bg-yellow-100">
                      <Activity className="text-yellow-600" size={24} />
                    </div>
                  </div>
                </div>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">Logs de Acesso</p>
                      <p className="text-2xl font-bold text-gray-900 mt-1">1.247</p>
                    </div>
                    <div className="p-3 rounded-lg bg-blue-100">
                      <Shield className="text-blue-600" size={24} />
                    </div>
                  </div>
                </div>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">Alertas Ativos</p>
                      <p className="text-2xl font-bold text-gray-900 mt-1">5</p>
                    </div>
                    <div className="p-3 rounded-lg bg-orange-100">
                      <Zap className="text-orange-600" size={24} />
                    </div>
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                {/* Logs de Acesso Detalhados */}
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Logs de Acesso em Tempo Real</h3>
                  <div className="space-y-3 max-h-96 overflow-y-auto">
                    {logsAuditoria.map((log, index) => (
                      <div key={index} className="flex items-start gap-4 p-3 bg-gray-50 rounded-lg">
                        <div className={`w-2 h-2 rounded-full mt-2 ${
                          log.status === 'sucesso' ? 'bg-green-500' : 'bg-red-500'
                        }`}></div>
                        <div className="flex-1">
                          <div className="flex justify-between items-start">
                            <p className="font-medium text-gray-900">{log.usuario}</p>
                            <span className="text-sm text-gray-500">{log.horario}</span>
                          </div>
                          <p className="text-sm text-gray-600">{log.acao}</p>
                          <div className="flex gap-4 mt-1">
                            <span className="text-xs text-gray-500">IP: {log.ip}</span>
                            <span className="text-xs text-gray-500">{log.dispositivo}</span>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Incidentes de Fraude */}
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">Incidentes de Fraude</h3>
                  <div className="space-y-4">
                    {incidentesFraude.map((incidente, index) => (
                      <div key={index} className={`p-4 border rounded-lg ${
                        incidente.severidade === 'alta' ? 'bg-red-50 border-red-200' :
                        'bg-yellow-50 border-yellow-200'
                      }`}>
                        <div className="flex items-start gap-3">
                          <AlertTriangle className={`mt-0.5 ${
                            incidente.severidade === 'alta' ? 'text-red-600' : 'text-yellow-600'
                          }`} size={20} />
                          <div className="flex-1">
                            <p className={`font-medium ${
                              incidente.severidade === 'alta' ? 'text-red-800' : 'text-yellow-800'
                            }`}>
                              {incidente.tipo}
                            </p>
                            <p className={`text-sm ${
                              incidente.severidade === 'alta' ? 'text-red-600' : 'text-yellow-600'
                            } mt-1`}>
                              {incidente.descricao}
                            </p>
                            <div className="flex gap-4 mt-2">
                              <span className="text-xs text-gray-500">CPF: {incidente.cpf}</span>
                              <span className="text-xs text-gray-500">{incidente.horario}</span>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>

                  {/* Estatísticas de Acesso */}
                  <div className="mt-6 p-4 bg-gray-50 rounded-lg">
                    <h4 className="font-medium text-gray-900 mb-3">Estatísticas de Acesso</h4>
                    <div className="grid grid-cols-2 gap-4 text-sm">
                      <div>
                        <p className="text-gray-600">Acessos Bem-sucedidos</p>
                        <p className="font-semibold text-green-600">98.7%</p>
                      </div>
                      <div>
                        <p className="text-gray-600">Tentativas com Erro</p>
                        <p className="font-semibold text-red-600">1.3%</p>
                      </div>
                      <div>
                        <p className="text-gray-600">Horário Pico</p>
                        <p className="font-semibold text-gray-900">14:00-16:00</p>
                      </div>
                      <div>
                        <p className="text-gray-600">Dispositivo Mais Usado</p>
                        <p className="font-semibold text-gray-900">Mobile (62%)</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          )}

        </div>
      </div>

      {/* MODAIS */}
      {modalAberto === 'abrir-conta' && (
        <ModalAbrirConta onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'encerrar-conta' && (
        <ModalEncerrarConta onClose={() => setModalAberto(null)} />
      )}
      {/* Adicionar outros modais conforme necessário */}

    </div>
  );
};

// Modal para Abrir Conta
const ModalAbrirConta = ({ onClose }) => {
  const [tipoConta, setTipoConta] = useState('CORRENTE');
  
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between p-6 border-b">
          <h2 className="text-xl font-bold text-gray-900">Abrir Nova Conta</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            ✕
          </button>
        </div>
        
        <div className="p-6 space-y-6">
          {/* Seleção do tipo de conta */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-3">Tipo de Conta</label>
            <div className="grid grid-cols-3 gap-4">
              {['CORRENTE', 'POUPANÇA', 'INVESTIMENTO'].map(tipo => (
                <button
                  key={tipo}
                  onClick={() => setTipoConta(tipo)}
                  className={`p-4 border-2 rounded-xl text-center transition-colors ${
                    tipoConta === tipo
                      ? 'border-blue-500 bg-blue-50 text-blue-700'
                      : 'border-gray-200 text-gray-600 hover:border-gray-300'
                  }`}
                >
                  <CreditCard className="mx-auto mb-2" size={24} />
                  <span className="font-medium">{tipo}</span>
                </button>
              ))}
            </div>
          </div>

          {/* Formulário de dados do cliente */}
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">CPF do Cliente</label>
                <input
                  type="text"
                  placeholder="000.000.000-00"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Agência</label>
                <select className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                  <option>001 - Centro</option>
                  <option>002 - Zona Sul</option>
                  <option>003 - Zona Norte</option>
                </select>
              </div>
            </div>

            {tipoConta === 'INVESTIMENTO' && (
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Perfil de Risco</label>
                <select className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                  <option>Conservador</option>
                  <option>Moderado</option>
                  <option>Agressivo</option>
                </select>
              </div>
            )}

            {tipoConta === 'CORRENTE' && (
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Limite Inicial</label>
                <input
                  type="text"
                  placeholder="R$ 1.000,00"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>
            )}
          </div>

          <div className="flex gap-4 pt-4">
            <button
              onClick={onClose}
              className="flex-1 py-3 px-4 border border-gray-300 text-gray-700 rounded-xl font-medium hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
            <button
              onClick={() => {
                toast.success('Conta criada com sucesso!');
                onClose();
              }}
              className="flex-1 bg-blue-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-blue-700 transition-colors"
            >
              Criar Conta
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

// Modal para Encerrar Conta
const ModalEncerrarConta = ({ onClose }) => {
  const [senha, setSenha] = useState('');
  
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-2xl max-w-md w-full">
        <div className="flex items-center justify-between p-6 border-b">
          <h2 className="text-xl font-bold text-gray-900">Encerrar Conta</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            ✕
          </button>
        </div>
        
        <div className="p-6 space-y-6">
          <div className="bg-red-50 border border-red-200 rounded-xl p-4">
            <div className="flex items-start gap-3">
              <AlertTriangle className="text-red-600 mt-0.5" size={20} />
              <div className="text-sm text-red-800">
                <strong>Atenção:</strong> Esta ação é irreversível. Todas as operações serão finalizadas.
              </div>
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Número da Conta</label>
            <input
              type="text"
              placeholder="00000-0"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Senha de Autorização</label>
            <input
              type="password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              placeholder="Digite sua senha de funcionário"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          <div className="flex gap-4">
            <button
              onClick={onClose}
              className="flex-1 py-3 px-4 border border-gray-300 text-gray-700 rounded-xl font-medium hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
            <button
              onClick={() => {
                if (senha) {
                  toast.success('Conta encerrada com sucesso!');
                  onClose();
                } else {
                  toast.error('Digite a senha de autorização');
                }
              }}
              className="flex-1 bg-red-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-red-700 transition-colors"
            >
              Encerrar Conta
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardFuncionario;