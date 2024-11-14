package application;

import model.entities.Sql;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Program {

    //altere o caminho para onde estará o seu xlsx
    private static final String fileName = "C:\\Users\\Luiz Antônio\\Documents\\doc\\tab2.xlsx";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        List<Sql> list = new ArrayList<>();

        try {
            FileInputStream arquivo = new FileInputStream(new File(Program.fileName));

            XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

            XSSFSheet sheetSql = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheetSql.iterator();

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Sql sql = new Sql();
                list.add(sql);
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            sql.setCodigo((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            sql.setPrice(cell.getNumericCellValue());
                            break;
                    }
                }
            }
            arquivo.close();

            File sourceFile = new File(Program.fileName);
            String sourceFolderStr = sourceFile.getParent();

            System.out.println("Nome da pasta para armazenar seus scripts: ");
            String pasta = sc.nextLine();
            boolean success = new File(sourceFolderStr + "\\" + pasta).mkdir();
            //criar uma pasta para armazenar os scripts apartir do caminho do xlsx
            System.out.println();

            while(success != true) {
                System.out.println("A pasta ja existe");
                System.out.println("Digite um nome para a nova pasta: ");
                pasta = sc.nextLine();
                success = new File(sourceFolderStr + "\\" + pasta).mkdir();
            }

            System.out.println("Digite aqui o nome do arquivo com o seu formato: ");
            String nomeArquivo = sc.nextLine();
            String targetSourceFolder = sourceFolderStr + "\\"+ pasta+"\\" + nomeArquivo;
            System.out.println(targetSourceFolder);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetSourceFolder))) {
                for(Sql item : list) {
                    bw.write("INSERT INTO TABELA_PRECO_AUX VALUES('" + item.getCodigo() + "'," + item.getPrice() + ");");
                    bw.newLine();
                }

            }
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            System.out.println("Programa encerrado!");
        }

    }
}
