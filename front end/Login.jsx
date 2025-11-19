// src/pages/Auth/Login.jsx - ATUALIZADO
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { Eye, EyeOff, Lock, User, Building, AlertCircle, Plus } from 'lucide-react';
import toast from 'react-hot-toast';

const Login = () => {
  const [identificador, setIdentificador] = useState('');
  const [senha, setSenha] = useState('');
  const [tipoUsuario, setTipoUsuario] = useState('CLIENTE');
  const [mostrarSenha, setMostrarSenha] = useState(false);
  const [loading, setLoading] = useState(false);
  const [horaAtual, setHoraAtual] = useState('');
  
  const { login, tentativasLogin } = useAuth();
  const navigate = useNavigate();

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

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const result = await login(identificador, senha, tipoUsuario);
    
    if (result.success) {
      toast.success('Login realizado com sucesso!');
      if (tipoUsuario === 'CLIENTE') {
        navigate('/cliente/dashboard');
      } else {
        navigate('/funcionario/dashboard');
      }
    } else {
      toast.error(result.message);
    }
    setLoading(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-blue-900 via-blue-700 to-blue-900">
      <div className="max-w-md w-full space-y-6">
        {/* Header com Logo e Hora */}
        <div className="text-center text-white">
          <div className="flex justify-center mb-4">
            <div className="bg-white text-blue-900 p-4 rounded-2xl shadow-2xl">
              <Building size={48} />
            </div>
          </div>
          <h1 className="text-4xl font-bold mb-2 tracking-tight">
            BANCO MALVADER
          </h1>
          <p className="text-blue-200 text-lg">Sistema Financeiro Seguro - v2.0</p>
          <div className="mt-2 text-blue-300 text-sm">
            {horaAtual} • Brasília/DF
          </div>
        </div>

        {/* Card Principal */}
        <div className="bg-white rounded-2xl shadow-2xl p-8 space-y-6 border border-blue-200">
          {/* Tipo de Usuário */}
          <div className="grid grid-cols-2 gap-4">
            <button
              type="button"
              onClick={() => setTipoUsuario('CLIENTE')}
              className={`p-4 rounded-xl border-2 transition-all ${
                tipoUsuario === 'CLIENTE'
                  ? 'border-blue-600 bg-blue-50 text-blue-700 shadow-md'
                  : 'border-gray-200 text-gray-600 hover:border-gray-300'
              }`}
            >
              <User className="mx-auto mb-2" size={24} />
              <span className="font-medium">Cliente</span>
            </button>

            <button
              type="button"
              onClick={() => setTipoUsuario('FUNCIONARIO')}
              className={`p-4 rounded-xl border-2 transition-all ${
                tipoUsuario === 'FUNCIONARIO'
                  ? 'border-blue-600 bg-blue-50 text-blue-700 shadow-md'
                  : 'border-gray-200 text-gray-600 hover:border-gray-300'
              }`}
            >
              <Building className="mx-auto mb-2" size={24} />
              <span className="font-medium">Funcionário</span>
            </button>
          </div>

          {/* Form de Login */}
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                {tipoUsuario === 'CLIENTE' ? 'CPF' : 'Matrícula'}
              </label>
              <div className="relative">
                <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                <input
                  type="text"
                  value={identificador}
                  onChange={(e) => setIdentificador(e.target.value)}
                  placeholder={tipoUsuario === 'CLIENTE' ? '000.000.000-00' : 'FUNC-001-2024'}
                  className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Senha
              </label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                <input
                  type={mostrarSenha ? "text" : "password"}
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                  placeholder="Digite sua senha"
                  className="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                />
                <button
                  type="button"
                  onClick={() => setMostrarSenha(!mostrarSenha)}
                  className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
                >
                  {mostrarSenha ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
              </div>
            </div>

            {/* Alertas de Segurança */}
            <div className="bg-yellow-50 border border-yellow-200 rounded-xl p-4">
              <div className="flex items-start">
                <AlertCircle className="text-yellow-600 mt-0.5 mr-3" size={20} />
                <div className="text-sm text-yellow-800">
                  <strong>Medidas de segurança:</strong>
                  <ul className="mt-1 list-disc list-inside space-y-1">
                    <li>3 tentativas de login permitidas</li>
                    <li>Bloqueio de 4 minutos após falhas</li>
                    <li>Token Google necessário para transferências</li>
                  </ul>
                </div>
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-blue-600 text-white py-3 px-4 rounded-xl font-medium hover:bg-blue-700 focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors disabled:opacity-50 disabled:cursor-not-allowed shadow-md"
            >
              {loading ? 'Entrando...' : 'Entrar no Sistema'}
            </button>
          </form>

          {/* Links Adicionais */}
          <div className="grid grid-cols-2 gap-4">
            <button
              onClick={() => navigate('/recuperacao-senha')}
              className="text-blue-600 hover:text-blue-700 font-medium text-sm text-center py-2 hover:bg-blue-50 rounded-lg transition-colors"
            >
              Esqueci minha senha
            </button>
            <button
              onClick={() => navigate('/criar-conta')}
              className="text-green-600 hover:text-green-700 font-medium text-sm text-center py-2 hover:bg-green-50 rounded-lg transition-colors flex items-center justify-center gap-2"
            >
              <Plus size={16} />
              Criar Conta
            </button>
          </div>
        </div>

        {/* Footer */}
        <div className="text-center text-blue-200 text-sm">
          <p>Banco Malvader &copy; 2024 - Sistema protegido por autenticação multifator</p>
        </div>
      </div>
    </div>
  );
};

export default Login;