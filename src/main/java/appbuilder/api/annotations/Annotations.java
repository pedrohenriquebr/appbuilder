package appbuilder.api.annotations;

public  final class Annotations {
    public static final int OVERRIDE = 255;

    public static final boolean HasAnnotation(int valor , int anotação){
        return (valor & anotação) == anotação;
    }
}