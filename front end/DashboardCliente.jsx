// src/pages/Cliente/DashboardCliente.jsx - ATUALIZADO
import React, { useState, useEffect } from 'react';
import { useAuth } from '../../contexts/AuthContext';
import {
  DollarSign, ArrowUpCircle, ArrowDownCircle,
  History, Shield, FileText, LogOut, Bell,
  CreditCard, Download, TrendingUp, PieChart,
  Settings, UserX, Investment
} from 'lucide-react';
import TransferenciaModal from '../../components/cliente/TransferenciaModal';
import SaldoModal from '../../components/cliente/SaldoModal';
import ExtratoModal from '../../components/cliente/ExtratoModal';
import TokenModal from '../../components/cliente/TokenModal';
import ComprovantesModal from '../../components/cliente/ComprovantesModal';
import InvestimentosModal from '../../components/cliente/InvestimentosModal';
import EncerrarContaModal from '../../components/cliente/EncerrarContaModal';

const DashboardCliente = () => {
  const { user, logout } = useAuth();
  const [modalAberto, setModalAberto] = useState(null);
  const [horaAtual, setHoraAtual] = useState('');
  const [saldoTotal, setSaldoTotal] = useState(12500.75);
  const [investimentos, setInvestimentos] = useState(4500.00);

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

  const menuItems = [
    {
      icon: DollarSign,
      label: 'Saldo',
      description: 'Ver saldo e projeções',
      onClick: () => setModalAberto('saldo'),
      color: 'text-green-600 bg-green-50'
    },
    {
      icon: ArrowDownCircle,
      label: 'Depósito',
      description: 'Realizar depósitos',
      onClick: () => setModalAberto('deposito'),
      color: 'text-blue-600 bg-blue-50'
    },
    {
      icon: ArrowUpCircle,
      label: 'Saque',
      description: 'Sacar valores',
      onClick: () => setModalAberto('saque'),
      color: 'text-orange-600 bg-orange-50'
    },
    {
      icon: History,
      label: 'Transferência',
      description: 'Transferir entre contas',
      onClick: () => setModalAberto('transferencia'),
      color: 'text-purple-600 bg-purple-50'
    },
    {
      icon: TrendingUp,
      label: 'Investimentos',
      description: 'Acompanhar investimentos',
      onClick: () => setModalAberto('investimentos'),
      color: 'text-teal-600 bg-teal-50'
    },
    {
      icon: FileText,
      label: 'Extrato',
      description: 'Últimas transações',
      onClick: () => setModalAberto('extrato'),
      color: 'text-gray-600 bg-gray-50'
    },
    {
      icon: CreditCard,
      label: 'Limite',
      description: 'Consultar limites',
      onClick: () => setModalAberto('limite'),
      color: 'text-red-600 bg-red-50'
    },
    {
      icon: Shield,
      label: 'Token',
      description: 'Gerenciar autenticação',
      onClick: () => setModalAberto('token'),
      color: 'text-indigo-600 bg-indigo-50'
    },
    {
      icon: Download,
      label: 'Comprovantes',
      description: 'Comprovantes de operações',
      onClick: () => setModalAberto('comprovantes'),
      color: 'text-cyan-600 bg-cyan-50'
    },
    {
      icon: PieChart,
      label: 'Relatórios',
      description: 'Relatórios financeiros',
      onClick: () => setModalAberto('relatorios'),
      color: 'text-pink-600 bg-pink-50'
    },
    {
      icon: Settings,
      label: 'Configurações',
      description: 'Configurar conta',
      onClick: () => setModalAberto('configuracoes'),
      color: 'text-yellow-600 bg-yellow-50'
    },
    {
      icon: UserX,
      label: 'Encerrar Conta',
      description: 'Solicitar encerramento',
      onClick: () => setModalAberto('encerrar-conta'),
      color: 'text-red-700 bg-red-100'
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
                <DollarSign size={24} />
              </div>
              <div className="ml-3">
                <h1 className="text-xl font-bold text-gray-900">Banco Malvader</h1>
                <p className="text-sm text-gray-600">Painel do Cliente</p>
              </div>
            </div>
            
            <div className="flex items-center space-x-6">
              <div className="text-right">
                <p className="text-sm font-medium text-gray-900">Bem-vindo, {user?.nome}</p>
                <p className="text-sm text-gray-600">{horaAtual} • Brasília/DF</p>
              </div>
              
              <div className="flex items-center space-x-2">
                <button
                  onClick={() => setModalAberto('configuracoes')}
                  className="p-2 text-gray-600 hover:text-gray-800 hover:bg-gray-100 rounded-lg transition-colors"
                  title="Configurações"
                >
                  <Settings size={20} />
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

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          {/* Status Bar */}
          <div className="bg-white rounded-xl shadow-sm p-6 mb-6">
            <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
              <div>
                <p className="text-sm text-gray-600">Saldo Disponível</p>
                <p className="text-2xl font-bold text-gray-900">R$ {(saldoTotal - investimentos).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Saldo Total</p>
                <p className="text-xl font-semibold text-gray-900">R$ {saldoTotal.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Investimentos</p>
                <p className="text-xl font-semibold text-teal-600">R$ {investimentos.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Conta</p>
                <p className="text-lg font-semibold text-gray-900">Poupança • 123456</p>
              </div>
              <div>
                <p className="text-sm text-gray-600">Token Google</p>
                <div className="flex items-center gap-2">
                  <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                  <span className="font-medium text-green-600">ATIVO</span>
                </div>
              </div>
            </div>
          </div>

          {/* Menu Grid */}
          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4 mb-8">
            {menuItems.map((item, index) => (
              <button
                key={index}
                onClick={item.onClick}
                className="bg-white rounded-xl shadow-sm p-4 text-left hover:shadow-md transition-shadow border border-gray-100 hover:border-blue-200 group"
              >
                <div className={`w-10 h-10 rounded-lg flex items-center justify-center mb-3 ${item.color} group-hover:scale-110 transition-transform`}>
                  <item.icon size={20} />
                </div>
                <h3 className="font-semibold text-gray-900 text-sm mb-1">{item.label}</h3>
                <p className="text-xs text-gray-600 leading-tight">{item.description}</p>
              </button>
            ))}
          </div>

          {/* Notificações e Gráficos */}
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Notificações */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <div className="flex items-center gap-3 mb-4">
                <Bell className="text-gray-600" size={20} />
                <h3 className="text-lg font-semibold text-gray-900">Notificações</h3>
              </div>
              <div className="space-y-3">
                <div className="flex items-center gap-3 p-3 bg-yellow-50 rounded-lg">
                  <div className="w-2 h-2 bg-yellow-500 rounded-full"></div>
                  <span className="text-sm text-yellow-800">
                    📍 2 comprovantes pendentes de download
                  </span>
                </div>
                <div className="flex items-center gap-3 p-3 bg-green-50 rounded-lg">
                  <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                  <span className="text-sm text-green-800">
                    📍 Transferência recebida: R$ 300,00 - JOANA
                  </span>
                </div>
                <div className="flex items-center gap-3 p-3 bg-blue-50 rounded-lg">
                  <div className="w-2 h-2 bg-blue-500 rounded-full"></div>
                  <span className="text-sm text-blue-800">
                    📍 Saque realizado hoje: R$ 150,00 - Agência 001
                  </span>
                </div>
                <div className="flex items-center gap-3 p-3 bg-teal-50 rounded-lg">
                  <div className="w-2 h-2 bg-teal-500 rounded-full"></div>
                  <span className="text-sm text-teal-800">
                    📈 Investimento rendeu +2.3% este mês
                  </span>
                </div>
              </div>
            </div>

            {/* Gráfico de Investimentos */}
            <div className="bg-white rounded-xl shadow-sm p-6 lg:col-span-2">
              <div className="flex items-center gap-3 mb-4">
                <Investment className="text-teal-600" size={20} />
                <h3 className="text-lg font-semibold text-gray-900">Performance dos Investimentos</h3>
              </div>
              <div className="space-y-4">
                <div className="flex justify-between items-center">
                  <span className="text-sm text-gray-600">CDB</span>
                  <div className="w-32 bg-gray-200 rounded-full h-2">
                    <div className="bg-green-500 h-2 rounded-full" style={{ width: '85%' }}></div>
                  </div>
                  <span className="text-sm font-medium text-green-600">+8.5%</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-sm text-gray-600">Tesouro Direto</span>
                  <div className="w-32 bg-gray-200 rounded-full h-2">
                    <div className="bg-blue-500 h-2 rounded-full" style={{ width: '72%' }}></div>
                  </div>
                  <span className="text-sm font-medium text-blue-600">+7.2%</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-sm text-gray-600">Fundos Imobiliários</span>
                  <div className="w-32 bg-gray-200 rounded-full h-2">
                    <div className="bg-purple-500 h-2 rounded-full" style={{ width: '65%' }}></div>
                  </div>
                  <span className="text-sm font-medium text-purple-600">+6.5%</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-sm text-gray-600">Ações</span>
                  <div className="w-32 bg-gray-200 rounded-full h-2">
                    <div className="bg-orange-500 h-2 rounded-full" style={{ width: '45%' }}></div>
                  </div>
                  <span className="text-sm font-medium text-orange-600">+4.5%</span>
                </div>
              </div>
              
              <div className="mt-6 p-4 bg-teal-50 rounded-lg">
                <div className="flex justify-between items-center">
                  <span className="text-sm font-medium text-teal-800">Rendimento Total Mensal</span>
                  <span className="text-lg font-bold text-teal-600">+R$ 245,80</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Modals */}
      {modalAberto === 'transferencia' && (
        <TransferenciaModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'saldo' && (
        <SaldoModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'extrato' && (
        <ExtratoModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'token' && (
        <TokenModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'comprovantes' && (
        <ComprovantesModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'investimentos' && (
        <InvestimentosModal onClose={() => setModalAberto(null)} />
      )}
      {modalAberto === 'encerrar-conta' && (
        <EncerrarContaModal onClose={() => setModalAberto(null)} />
      )}
    </div>
  );
};

export default DashboardCliente;