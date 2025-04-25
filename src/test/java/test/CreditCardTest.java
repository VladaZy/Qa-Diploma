package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentTypesPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditCardTest {


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
    public void shouldCreditPaymentApproved() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCreditPayment());

    }

    @Test
    public void shouldDeclinedCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.bankDeclinedOperation();
        Assertions.assertEquals("Declined", SQLHelper.getCreditPayment());
    }


    @Test
    public void shouldIncorrectCreditPaymenNumber() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var invalidCardNumber = DataHelper.getAShortNumber();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void shouldMustBlankFieldCardNumberCreditPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(emptyCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorFormat();

    }

    @Test
    public void creditPaymentByCardWithExpiredMonthlyValidity() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var currentYear = DataHelper.getRandomYear(0);
        var monthExpired = DataHelper.getRandomMonth(-2);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, monthExpired, currentYear, validOwnerName, validCode);
        creditCardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithTheWrongMonth() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACreditCardWithAnEmptyMonth() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyMonth = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, emptyMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void creditPaymentByCardWithExpiredAnnualValidity() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var AnnualValidity = DataHelper.getRandomYear(-5);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, AnnualValidity, validOwnerName, validCode);
        creditCardPage.termValidityExpired();

    }

    @Test
    public void creditCardPaymentWithEmptyYear() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyYear = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, emptyYear, validOwnerName, validCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void rusLanguageNamePaymentByCreditCard() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void digitsNameCardCreditPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var digitsName = DataHelper.getNumberName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, digitsName, validCode);
        creditCardPage.errorFormat();
    }


    @Test
    public void specSymbolsNameCreditPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var specSymbolsName = DataHelper.getSpecialCharactersName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specSymbolsName, validCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void emptyNameCreditPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyName = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        creditCardPage.emptyField();
    }

    @Test
    public void twoDigitCreditPaymentCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var twoDigitCard = DataHelper.getNumberCVC(2);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCard);
        creditCardPage.errorFormat();
    }

    @Test
    public void oneDigitInTheCreditPaymentCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var oneDigitInTheCard = DataHelper.getNumberCVC(1);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitInTheCard);
        creditCardPage.errorFormat();
    }

    @Test
    public void emptyFieldInTheCreditCardCode() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyField = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, emptyField);
        creditCardPage.errorFormat();

    }

    @Test
    public void specSymbolsCodeCreditCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var specSymbolsCode = DataHelper.getSpecialCharactersName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, specSymbolsCode);
        creditCardPage.errorFormat();
    }

    @Test
    public void emptyAllFieldsCreditCardPayment() {
        PaymentTypesPage page = new PaymentTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        var emptyMonth = DataHelper.getEmptyField();
        var emptyYear = DataHelper.getEmptyField();
        var emptyOwnerName = DataHelper.getEmptyField();
        var emptyCode = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwnerName, emptyCode);
        creditCardPage.errorFormat();


    }

}