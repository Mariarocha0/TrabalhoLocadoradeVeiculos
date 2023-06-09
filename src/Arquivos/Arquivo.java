package Arquivos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Arquivo {

    private String filePath;
    private String content;

    public Arquivo(String filePath) {
        if(filePath == null || filePath.trim().isEmpty()) {
            throw new NullPointerException("Caminho do arquivo não pode ser nulo!");
        }

        this.filePath = filePath;
        this.content = "";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        if(filePath == null || filePath.trim().isEmpty()) {
            throw new NullPointerException("Caminho do arquivo não pode ser nulo!");
        }

        this.filePath = filePath;
    }

    public void read() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.filePath));

            String line = null;
            this.content = "";

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                for (String value : values) {
                    this.content += value + ";";
                }
                this.content += "\n";
            }

            if(this.content.trim().isEmpty()) {
                throw new NullPointerException("Arquivo vazio!");
            }

        } catch (IOException e) {
            throw new NullPointerException("Arquivo não encontrado!");
        } catch (NullPointerException e) {
            throw new NullPointerException("Arquivo vazio!");
        } catch(Exception e) {
            throw new NullPointerException("Erro inesperado ao ler o arquivo!");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new NullPointerException("Erro ao fechar o arquivo!");
                } catch(Exception e) {
                    throw new NullPointerException("Erro inesperado ao fechar o arquivo!");
                }
            }
        }
    }

    public String getContent() {
        if(this.content == null || this.content.trim().isEmpty()) {
            throw new NullPointerException("Arquivo não lido anteriormente!");
        }

        return this.content;
    }

}