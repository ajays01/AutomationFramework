package org.automation.utility;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.automation.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PageFunctions extends BasePage {

    public static void clickElement(WebElement element) {
        int noOfUiTries;
        noOfUiTries = Integer.parseInt(Param.getProperty("noOfUiTries"));
        for(int i=0; i<= noOfUiTries; i++){
            try{
                element.click();
                break;
            }catch (Exception e){
                simpleWait(200);
                if(i == noOfUiTries -1){
                    Add_log.error("Error in Click by Element method - "+e);
                    throw e;
                }
            }
        }
    }

    public static void simpleWait(int waitInMilliSec){
        try {
            TimeUnit.MILLISECONDS.sleep(waitInMilliSec);
        }catch (InterruptedException e){
            Add_log.error("Error in simple wait method - "+e);
        }
    }

    public static void enterText(WebElement element, String text){
        try{
            jsClick(element);
            element.clear();
            element.sendKeys(text);
            Add_log.info("Text entered - ");
        }catch (Exception e) {
            clickElement(element);
            element.sendKeys(text);
            Add_log.info("Text entered - ");
        }
    }

    public static void frame(String frameName){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(frameName);
        Add_log.info("Switched to - "+frameName);
    }

    public static void iframe(String frameName){
        driver.switchTo().frame(frameName);
        Add_log.info("Switched to - "+frameName);
    }

    public static void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("argument[0].click()", element);
        Add_log.info("Clicked using javascript executor");
    }

    public static void dropDown(WebElement element, String ddName) {
        clickElement(element);
        new Select(element).selectByVisibleText(ddName);
        Add_log.info("Selected dropdown as -> " + ddName);
    }

    public static void dropDownByIndex(WebElement element, int index) {
        clickElement(element);
        new Select(element).selectByIndex(index);
        Add_log.info("Selected dropdown using index -> " + index);
    }

    public static void createWordDoc(String testCaseNum) throws IOException, InvalidFormatException {
        testCaseNum = testCaseNum.trim();
        String directory = System.getProperty("user.dir") + "//screenshots//" + testCaseNum;
        try {
            XWPFDocument docx = new XWPFDocument();
            XWPFRun run = docx.createParagraph().createRun();
            //setting margin as narrow
            CTSectPr sectPr = docx.getDocument().getBody().getSectPr();
            if (sectPr == null) sectPr = docx.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.getPgMar();
            if (sectPr == null) pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(720));//720 twentienths of an inch point (Twips) = 720/20 = 36pt = 36/72=0.5"
            pageMar.setRight(BigInteger.valueOf(720));
            pageMar.setTop(BigInteger.valueOf(720));
            pageMar.setBottom(BigInteger.valueOf(720));
            pageMar.setFooter(BigInteger.valueOf(720));
            pageMar.setHeader(BigInteger.valueOf(720));
            pageMar.setGutter(BigInteger.valueOf(720));

            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY__hh_mm__ssaa");
            FileOutputStream out = new FileOutputStream(directory + "//" + testCaseNum + "-" + dateFormat.format(new Date()) + ".docx");
            Add_log.info(directory);
            File file = new File(directory + "//");
            File[] files = file.listFiles();
            List<String> flist = new ArrayList<>();
            if (files != null) {
                for (File f : files) {
                    String extension = FilenameUtils.getExtension(f.getName());
                    if (extension.equalsIgnoreCase("png")) {
                        flist.add(f.getName());
                        InputStream pic = new FileInputStream(f);
                        run.addBreak();
                        run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, f.getName(), Units.toEMU(545), Units.toEMU(275));
                        pic.close();
                    }
                }
                docx.write(out);
                out.flush();
                out.close();
                docx.close();
                flist.forEach(ele -> {
                    File fi = new File(directory + "//" + ele);
                    fi.delete();
                });
            }
        } catch (Exception e) {
            Add_log.info(e);
        }
    }
    public static void failureCreateWordDoc(String testCaseNum) throws IOException, InvalidFormatException {
        testCaseNum = testCaseNum.trim();
        String directory = System.getProperty("user.dir") + "//screenshots//" + testCaseNum;
        try {
            XWPFDocument docx = new XWPFDocument();
            XWPFRun run = docx.createParagraph().createRun();
            //setting margin as narrow
            CTSectPr sectPr = docx.getDocument().getBody().getSectPr();
            if (sectPr == null) sectPr = docx.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.getPgMar();
            if (sectPr == null) pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(720));//720 twentienths of an inch point (Twips) = 720/20 = 36pt = 36/72=0.5"
            pageMar.setRight(BigInteger.valueOf(720));
            pageMar.setTop(BigInteger.valueOf(720));
            pageMar.setBottom(BigInteger.valueOf(720));
            pageMar.setFooter(BigInteger.valueOf(720));
            pageMar.setHeader(BigInteger.valueOf(720));
            pageMar.setGutter(BigInteger.valueOf(720));

            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY__hh_mm__ssaa");
            FileOutputStream out = new FileOutputStream(directory + "//" +"Fail_"+ testCaseNum + "-" + dateFormat.format(new Date()) + ".docx");
            Add_log.info(directory);
            File file = new File(directory + "//");
            File[] files = file.listFiles();
            List<String> flist = new ArrayList<>();
            if (files != null) {
                for (File f : files) {
                    String extension = FilenameUtils.getExtension(f.getName());
                    if (extension.equalsIgnoreCase("png")) {
                        flist.add(f.getName());
                        InputStream pic = new FileInputStream(f);
                        run.addBreak();
                        run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, f.getName(), Units.toEMU(545), Units.toEMU(275));
                        pic.close();
                    }
                }
                docx.write(out);
                out.flush();
                out.close();
                docx.close();
                flist.forEach(ele -> {
                    File fi = new File(directory + "//" + ele);
                    fi.delete();
                });
            }
        } catch (Exception e) {
            Add_log.info(e);
        }
    }

    public static void takeSnapShotWithText(WebDriver driver, String testCaseNum, String textMsg) throws IOException {
        testCaseNum=testCaseNum.trim();
        try{
            String directory="";
            TakesScreenshot scrShot = ((TakesScreenshot)driver);
            File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY__hh_mm_ssaa");
            String destFile = testCaseNum+"-"+dateFormat.format(new Date())+".png";
            directory = System.getProperty("user.dir")+"//screenshots//"+testCaseNum;
            new File(directory).mkdir();
            FileUtils.copyFile(scrFile,new File(directory+"/"+destFile));
            //Write text to file
            final BufferedImage image = ImageIO.read(new File(directory+"/"+destFile));//reading file
            Graphics g = image.getGraphics();
            g.setFont(g.getFont().deriveFont(30f));
            g.setColor(Color.BLACK);
            g.drawString(textMsg,150,600);
            g.dispose();
            ImageIO.write(image,"png",new File(directory+"/"+destFile));//writing file
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

