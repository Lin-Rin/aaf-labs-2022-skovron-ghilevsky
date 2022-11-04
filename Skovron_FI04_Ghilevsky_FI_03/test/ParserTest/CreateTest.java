package Skovron_FI04_Ghilevsky_FI_03.test.ParserTest;

import Skovron_FI04_Ghilevsky_FI_03.main.Parser.Query.Create;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTest {
    @Test
    void regularCreateCase1Test(){
        Create create = new Create("create student (age, weight)");
        String[] colName = new String[]{"age", "weight"};

        Assertions.assertEquals("student", create.getTableName());
        Assertions.assertEquals(colName[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colName[1], create.getNameOfColum()[1]);
    }

    @Test
    void regularCreateCase2Test(){
        Create create = new Create("create student (age, weight, height)");

        String[] colName = new String[]{"age", "weight", "height"};

        Assertions.assertEquals("student", create.getTableName());
        Assertions.assertEquals(colName[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colName[1], create.getNameOfColum()[1]);
        Assertions.assertEquals(colName[2], create.getNameOfColum()[2]);
    }

    @Test
    void regularCreateCase3Test(){
        Create create = new Create("create \"student id\" (age, weight, height)");

        String[] colName = new String[]{"age", "weight", "height"};

        Assertions.assertEquals("student id", create.getTableName());
        Assertions.assertEquals(colName[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colName[1], create.getNameOfColum()[1]);
        Assertions.assertEquals(colName[2], create.getNameOfColum()[2]);
    }

    @Test
    void regularCreateCase4Test(){
        Create create = new Create("create \"student id\" (\"student age\", weight, \"student height\")");

        String[] colName = new String[]{"student age", "weight", "student height"};

        Assertions.assertEquals("student id", create.getTableName());
        Assertions.assertEquals(colName[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colName[1], create.getNameOfColum()[1]);
        Assertions.assertEquals(colName[2], create.getNameOfColum()[2]);
    }

    @Test
    void regularCreateCase5Test(){
        Create create = new Create("CreAtE student (age, weight, height)");

        String[] colName = new String[]{"age", "weight", "height"};

        Assertions.assertEquals("student", create.getTableName());
        Assertions.assertEquals(colName[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colName[1], create.getNameOfColum()[1]);
        Assertions.assertEquals(colName[2], create.getNameOfColum()[2]);
    }

    @Test
    void emptyNameCreateTest(){
        Create create = new Create("create (age, weight)");

        String[] colname = new String[]{"age", "weight"};

        Exception exception = assertThrows(RuntimeException.class, create::getTableName);
        String expectedMessage = "Error: Empty table name";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        Assertions.assertEquals(colname[0], create.getNameOfColum()[0]);
        Assertions.assertEquals(colname[1], create.getNameOfColum()[1]);
    }

    @Test
    void emptyColumnNameCreateTest(){
        Create create = new Create("create student ()");

        Assertions.assertEquals("student", create.getTableName());

        Exception exception = assertThrows(RuntimeException.class, create::getNameOfColum);
        String expectedMessage = "Error: Empty colum name";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}