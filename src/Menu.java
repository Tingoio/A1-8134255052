import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    
    public void iniciar(){
        String alunosFile = "alunos.csv";
        List<Aluno> listaAlunos = new ArrayList<>();
    
        // Leitura do arquivo alunos.csv
        try (BufferedReader br = new BufferedReader(new FileReader(alunosFile))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String matricula = values[0];
                String nome = values[1];
                double nota = Double.parseDouble(values[2].replace(',', '.'));
                Aluno aluno = new Aluno(matricula, nome, nota);
                listaAlunos.add(aluno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Aluno aluno : listaAlunos) {
            System.out.println(aluno);
        }

        //Registro no arquivo resumo.csv
        int quantidadeAlunos = listaAlunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;
        
        for (Aluno aluno : listaAlunos) {
            if (aluno.getNota() >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (aluno.getNota() < menorNota) {
                menorNota = aluno.getNota();
            }
            if (aluno.getNota() > maiorNota) {
                maiorNota = aluno.getNota();
            }
            somaNotas += aluno.getNota();
        }

        double media = somaNotas / quantidadeAlunos;

        // Escrevendo os dados no arquivo resumo.csv
        String resumoFile = "resumo.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resumoFile))) {
            bw.write("quantidade_alunos;aprovados;reprovados;menor_nota;maior_nota;media");
            bw.newLine();
            bw.write(quantidadeAlunos + ";" + aprovados + ";" + reprovados + ";" + menorNota + ";" + maiorNota + ";" + media);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Exibindo os dados calculados para verificar
        System.out.println("Quantidade de alunos: " + quantidadeAlunos);
        System.out.println("Aprovados: " + aprovados);
        System.out.println("Reprovados: " + reprovados);
        System.out.println("Menor nota: " + menorNota);
        System.out.println("Maior nota: " + maiorNota);
        System.out.println("Média da turma: " + media);
    }
}
