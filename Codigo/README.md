# Sistema de Gerenciamento de Horas de Extensão

Sistema desenvolvido em Java Swing com arquitetura MVC para gerenciamento de horas de extensão de alunos e professores.

## Estrutura do Projeto

```
src/
├── model/          # Camada de modelo (Usuario, Professor, Aluno)
├── controller/     # Controladores (ControladorProfessor, ControladorAluno)
├── view/           # Interfaces gráficas Swing
├── repository/     # Catálogos/Repositórios de dados
└── persistence/    # Gerenciamento de persistência por serialização
```

## Casos de Uso Implementados

### UC01 - Tela Inicial
- Menu principal com opções de registro e login
- Carrega dados persistidos ao iniciar
- Salva dados ao fechar o sistema

### UC02 - Registrar Professor
- Formulário de cadastro com validações
- Campos: Nome, Email, Senha, CPF, Curso
- Validação de email e CPF únicos

### UC03 - Login Professor
- Autenticação por email e senha
- Feedback de sucesso/erro

### UC04 - Registrar Aluno
- Formulário de cadastro com validações
- Campos: Nome, Email, Senha, CPF, Curso, RA
- Validação de email, CPF e RA únicos
- Horas acumuladas inicializadas em 0.0

### UC05 - Login Aluno
- Autenticação por email e senha
- Exibe horas acumuladas ao fazer login

## Como Compilar e Executar

### Compilar todas as classes:
```bash
cd /mnt/e/UNESP/10Semestre/ESII/Projeto-de-Horas-Extensao/Codigo/src
javac model/*.java repository/*.java controller/*.java persistence/*.java view/*.java
```

### Executar o sistema:
```bash
java view.TelaInicial
```

## Persistência

Os dados são salvos automaticamente em `dados_sistema.dat` ao fechar a aplicação e carregados ao iniciar.

## Arquitetura MVC

- **Model**: Classes de domínio (Usuario, Professor, Aluno)
- **View**: Telas Swing (TelaInicial, TelaRegistroProfessor, etc.)
- **Controller**: Lógica de negócio e validações (ControladorProfessor, ControladorAluno)
- **Repository**: Gerenciamento de coleções (CatalogoProfessor, CatalogoAluno)
- **Persistence**: Serialização de dados (GerenciadorPersistencia)

## Observações

- Os avisos de lint sobre pacotes podem ser ignorados - a estrutura de pacotes está correta
- O sistema salva automaticamente ao fechar a janela principal
- Todas as validações são feitas antes de persistir os dados
