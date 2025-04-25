package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentTypesPage;

import static com.codeborne.selenide.Selenide.open;


public class DebitCardTest {

    String approvedCardNumber = DataHelper.getCardApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getCardDeclined().getCardNumber();
    String randomCardNumber = DataHelper.getRandomCardNumber();
    String validMonth = DataHelper.getRandomMonth(1);
    String validYear = DataHelper.getRandomYear(1);
    String validOwnerName = DataHelper.getRandomName();
    String validCode = DataHelper.getNumberCVC(3);


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");

    }

    @AfterEach
    public void shouldCleanBase() {
        SQLHelper.сleanBase();
    }

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    public void shouldCardPaymentApproved() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCardPayment());

    }

    @Test
    public void shouldDeclinedCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.bankDeclinedOperation();
        Assertions.assertEquals("DECLINED", SQLHelper.getCardPayment());
    }


    @Test
    public void shouldIncorrectPaymentNumber() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidCardNumber = DataHelper.getAShortNumber();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void shouldMustBlankFieldCardNumberPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(emptyCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();

    }

    @Test
    public void paymentByCardWithExpiredMonthlyValidity() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var currentYear = DataHelper.getRandomYear(0);
        var monthExpired = DataHelper.getRandomMonth(-2);
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, monthExpired, currentYear, validOwnerName, validCode);
        debitCardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithTheWrongMonth() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithAnEmptyMonth() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyMonth = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, emptyMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void paymentByCardWithExpiredAnnualValidity() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var AnnualValidity = DataHelper.getRandomYear(-5);
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, AnnualValidity, validOwnerName, validCode);
        debitCardPage.termValidityExpired();

    }

    @Test
    public void cardPaymentWithEmptyYear() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyYear = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, emptyYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void rusLanguageNamePaymentByCard() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void digitsNameCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var digitsName = DataHelper.getNumberName();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, digitsName, validCode);
        debitCardPage.errorFormat();
    }


    @Test
    public void specSymbolsNameCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var specSymbolsName = DataHelper.getSpecialCharactersName();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specSymbolsName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void emptyNameCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyName = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        debitCardPage.emptyField();
    }

    @Test
    public void twoDigitCardPaymentCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var twoDigitCard = DataHelper.getNumberCVC(2);
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCard);
        debitCardPage.errorFormat();
    }

    @Test
    public void oneDigitInTheCardPaymentCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var oneDigitInTheCard = DataHelper.getNumberCVC(1);
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitInTheCard);
        debitCardPage.errorFormat();
    }

    @Test
    public void emptyPaymentFieldInTheCardCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyField = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, emptyField);
        debitCardPage.errorFormat();

    }

    @Test
    public void  specSymbolsCodeCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var specSymbolsCode = DataHelper.getSpecialCharactersName();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, specSymbolsCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void emptyAllFieldsCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        var emptyMonth = DataHelper.getEmptyField();
        var emptyYear = DataHelper.getEmptyField();
        var emptyOwnerName = DataHelper.getEmptyField();
        var emptyCode = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwnerName, emptyCode);
        debitCardPage.errorFormat();


    }

}