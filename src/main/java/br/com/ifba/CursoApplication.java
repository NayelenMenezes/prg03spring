package br.com.ifba;

import br.com.ifba.curso.view.CursoListar;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CursoApplication {

    public static void main(String[] args) {
        // Força o sistema a permitir interfaces gráficas (AWT/Swing)
        System.setProperty("java.awt.headless", "false");

        // Inicializa o contexto do Spring Boot no modo Desktop
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CursoApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // Abre a tela na Thread de Eventos do Swing
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // Aplica o visual Nimbus
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                
                //Pega a TELA direto do gerenciador do Spring Boot!
                CursoListar tela = context.getBean(CursoListar.class);
                
                // Centraliza e força a exibição
                tela.setLocationRelativeTo(null); 
                tela.setVisible(true);
                
                System.out.println("====== TELA INICIALIZADA COM SUCESSO ======");
                
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(CursoApplication.class.getName())
                        .log(java.util.logging.Level.SEVERE, "Erro crítico na inicialização da UI", ex);
            }
        });
    }
}