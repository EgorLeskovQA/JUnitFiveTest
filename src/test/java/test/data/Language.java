package test.data;

public enum Language {
    Transport("Не только товары"),
    House("И новое, и подержанное"),
    Sc("Всё под защитой");


    public final String description;
    Language(String description) {
        this.description = description;
    }
}
