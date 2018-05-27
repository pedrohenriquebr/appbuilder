package appbuilder.api.annotations;

public final class Anotação {
    public static final int OVERRIDE = 255;

    public static final boolean temAnotação(int valor , int anotação){
        return (valor & anotação) == anotação;
    }
}