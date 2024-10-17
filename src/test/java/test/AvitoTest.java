package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import test.data.Language;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AvitoTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://www.avito.ru";
        Configuration.pageLoadStrategy = "eager";
        //Configuration.holdBrowserOpen = true;
    }

    @DisplayName("Выдача по предложениям не пустая")
    @ValueSource(strings = {
            "Авто", "Квартира"
    })
    @ParameterizedTest(name = "Выдача поиска на главной странице {0} не пустая")
    @Tag("SMOKE")
    void searchResultsShouldNotBeEmptyTest(String searchQuery) {
        open("/");
        $("input.styles-module-input-Lisnt").setValue(searchQuery).pressEnter();
        $$(".items-items-kAJAg")
                .shouldBe(sizeGreaterThan(0));
    }


    @DisplayName("Корректная поисковая выдача")
    @CsvFileSource(resources = "/test_data/avitoTest.csv")
    @ParameterizedTest(name = "Для поискового запроса {0} первой в выдаче должна быть строка - {1}")
    @Tag("REGRESS")
    void searchMatchesTheOutputTest(String searchQuery, String expectedString) {
        open("/");
        $("input.styles-module-input-Lisnt").setValue(searchQuery).pressEnter();
        $$(".page-title-text-tSffu")
                .shouldHave(texts(expectedString));
    }

    @DisplayName("Отображение услуг")
    @EnumSource(Language.class)
    @ParameterizedTest(name = "Проверка услуи {0}")
    @Tag("REGRESS")

    void checkAttributesTest(Language language) {
        open("/business");
        $(".cards-with-icon-module-cards-vmqjX")
                .shouldHave(text(language.description));
    }

}