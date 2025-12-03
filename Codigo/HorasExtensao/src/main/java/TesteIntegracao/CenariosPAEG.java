package TesteIntegracao;

import com.mycompany.horasextensao.Sistema;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.*;

public class CenariosPAEG {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static Date d(String s) {
        try {
            return sdf.parse(s);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static void gerar() {

        System.out.println(">>> [TESTE] Gerando CENÁRIO COMPLETO de integração...");

        // =====================================================================
        // 1. PROFESSORES
        // =====================================================================
        Professor carol = new Professor("Carol Silva", "45678912311",
                "carol@unesp.br", "Senha123!", Cursos.COMPUTACAO);

        Professor lais = new Professor("Lais Oliveira", "78945612366",
                "lais@unesp.br", "Senha123!", Cursos.COMPUTACAO);

        Professor renato = new Professor("Renato Dias", "55566677788",
                "renato@unesp.br", "Senha123!", Cursos.MATEMATICA);

        Sistema.catalogoProfessor.getProfessores().add(carol);
        Sistema.catalogoProfessor.getProfessores().add(lais);
        Sistema.catalogoProfessor.getProfessores().add(renato);

        // =====================================================================
        // 2. ALUNOS
        // =====================================================================
        Aluno isabella = new Aluno("Isabella Souza", "49165006879",
                "isa@unesp.br", "Senha123!", Cursos.COMPUTACAO, "10011");

        Aluno pedro = new Aluno("Pedro Ramos", "11223344556",
                "pedro@unesp.br", "Senha123!", Cursos.MATEMATICA, "20022");

        Aluno joao = new Aluno("João Vieira", "99988877766",
                "joao@unesp.br", "Senha123!", Cursos.COMPUTACAO, "30033");

        Aluno aline = new Aluno("Aline Costa", "88877766655",
                "aline@unesp.br", "Senha123!", Cursos.COMPUTACAO, "40044");

        Sistema.catalogoAluno.getAlunos().add(isabella);
        Sistema.catalogoAluno.getAlunos().add(pedro);
        Sistema.catalogoAluno.getAlunos().add(joao);
        Sistema.catalogoAluno.getAlunos().add(aline);

        // =====================================================================
        // 3. PROJETOS E ATIVIDADES
        // =====================================================================
        Projeto projComp = new Projeto("Projeto Algoritmos",
                "Reforço de lógica, ED e práticas",
                Cursos.COMPUTACAO,
                carol);

        projComp.getProfessores().add(lais); // membro adicional
        Sistema.catalogoProjeto.getProjetos().add(projComp);

        Atividade atvComp = new Atividade("Monitoria Básica",
                "Aulas de reforço e monitoria",
                projComp);

        projComp.getAtividades().add(atvComp);
        Sistema.catalogoAtividade.getAtividades().add(atvComp);

        // Projeto Matemática
        Projeto projMat = new Projeto("Projeto Cálculo",
                "Estudos dirigidos e resoluções",
                Cursos.MATEMATICA,
                renato);

        Sistema.catalogoProjeto.getProjetos().add(projMat);

        Atividade atvMat = new Atividade("Monitoria Cálculo",
                "Revisão de limites, derivadas e integrais",
                projMat);

        Sistema.catalogoAtividade.getAtividades().add(atvMat);

        // =====================================================================
        // 4. PAEGs
        // =====================================================================
        // ---------------------------------------------------------
        // 4.1 PAEG ABERTO (só CANDIDATURA)
        // ---------------------------------------------------------
        // Hoje: 30/11/2025
        // Fica ABERTO de 25/11 a 05/12
        PAEG paegAberto = new PAEG(
                "PAEG_ABERTO",
                20,
                5,
                d("25/11/2025"), d("05/12/2025"), // candidatura ABERTA
                d("10/12/2025"), d("20/12/2025"), // execução futura
                atvComp
        );
        atvComp.getPaegs().add(paegAberto);
        Sistema.catalogoPAEG.getPaegs().add(paegAberto);

        // ---------------------------------------------------------
        // 4.2 PAEG FECHADO (período de candidatura ACABOU)
        //     → usar para APROVAR / REPROVAR
        // ---------------------------------------------------------
        // Candidatura terminou antes de 30/11, execução futura
        PAEG paegFechado = new PAEG(
                "PAEG_FECHADO",
                20,
                2, // limite de 2 aprovações
                d("15/11/2025"), d("25/11/2025"), // já FECHADO
                d("10/12/2025"), d("20/12/2025"), // execução ainda não começou
                atvComp
        );
        atvComp.getPaegs().add(paegFechado);
        Sistema.catalogoPAEG.getPaegs().add(paegFechado);

        // 3 pendentes → para testar limite de vagas na aprovação
        Candidatura f1 = new Candidatura(isabella, paegFechado);
        Candidatura f2 = new Candidatura(joao, paegFechado);
        Candidatura f3 = new Candidatura(aline, paegFechado);

        paegFechado.getCandidaturas().add(f1);
        paegFechado.getCandidaturas().add(f2);
        paegFechado.getCandidaturas().add(f3);

        Sistema.catalogoCandidatura.getCandidaturas().add(f1);
        Sistema.catalogoCandidatura.getCandidaturas().add(f2);
        Sistema.catalogoCandidatura.getCandidaturas().add(f3);

        // ---------------------------------------------------------
        // 4.3 PAEG EM EXECUÇÃO (execução começou mas AINDA NÃO acabou)
        //     → tentar registrar presença e DEVE DAR ERRO (data)
        // ---------------------------------------------------------
        // Candidatura já fechou, execução de 28/11 a 05/12
        PAEG paegExec = new PAEG(
                "PAEG_EXECUCAO",
                20,
                3,
                d("10/11/2025"), d("20/11/2025"), // fechado
                d("28/11/2025"), d("05/12/2025"), // estamos DENTRO da execução (30/11)
                atvComp
        );
        atvComp.getPaegs().add(paegExec);
        Sistema.catalogoPAEG.getPaegs().add(paegExec);

        // Todos aprovados, mas dataFinalExecução ainda não passou
        Candidatura e1 = new Candidatura(isabella, paegExec);
        e1.setStatus(StatusCandidatura.APROVADO);

        Candidatura e2 = new Candidatura(joao, paegExec);
        e2.setStatus(StatusCandidatura.APROVADO);

        paegExec.getCandidaturas().add(e1);
        paegExec.getCandidaturas().add(e2);

        Sistema.catalogoCandidatura.getCandidaturas().add(e1);
        Sistema.catalogoCandidatura.getCandidaturas().add(e2);

        // ---------------------------------------------------------
        // 4.4 PAEG CONCLUÍDO (EXECUÇÃO TERMINOU)
        //     → todos JÁ APROVADOS, só marcar PRESENÇA / AUSENTE
        // ---------------------------------------------------------
        PAEG paegConc = new PAEG(
                "PAEG_CONCLUIDO",
                20,
                3,
                d("01/11/2025"), d("05/11/2025"),
                d("10/11/2025"), d("20/11/2025"), // execução JÁ ACABOU
                atvComp
        );
        atvComp.getPaegs().add(paegConc);
        Sistema.catalogoPAEG.getPaegs().add(paegConc);

        Candidatura c1 = new Candidatura(isabella, paegConc);
        c1.setStatus(StatusCandidatura.APROVADO);

        Candidatura c2 = new Candidatura(aline, paegConc);
        c2.setStatus(StatusCandidatura.APROVADO);

        paegConc.getCandidaturas().add(c1);
        paegConc.getCandidaturas().add(c2);

        Sistema.catalogoCandidatura.getCandidaturas().add(c1);
        Sistema.catalogoCandidatura.getCandidaturas().add(c2);

        // ---------------------------------------------------------
        // 4.5 PAEG MATEMÁTICA (candidatura aberta SÓ para Matemática)
        // ---------------------------------------------------------
        PAEG paegMat = new PAEG(
                "PAEG_MATEMATICA",
                20,
                2,
                d("25/11/2025"), d("05/12/2025"), // aberto
                d("15/12/2025"), d("20/12/2025"),
                atvMat
        );
        atvMat.getPaegs().add(paegMat);
        Sistema.catalogoPAEG.getPaegs().add(paegMat);

        // ---------------------------------------------------------
        //  PAEG CONCLUÍDO A — para testar horas acumuladas
        // ---------------------------------------------------------
        PAEG paegConcA = new PAEG(
                "PAEG_CONCLUIDO_A",
                20, // 20 horas
                5,
                d("01/11/2025"), d("05/11/2025"),
                d("10/11/2025"), d("15/11/2025"), // execução JÁ ACABOU
                atvComp
        );
        atvComp.getPaegs().add(paegConcA);
        Sistema.catalogoPAEG.getPaegs().add(paegConcA);

        Candidatura concA1 = new Candidatura(isabella, paegConcA);
        concA1.setStatus(StatusCandidatura.APROVADO);

        paegConcA.getCandidaturas().add(concA1);
        Sistema.catalogoCandidatura.getCandidaturas().add(concA1);

        System.out.println(">>> [TESTE] CENÁRIO COMPLETO criado!");
    }
}
