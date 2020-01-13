package jordibarea.tfg;

public class Card {
    private int value;
    private int type;
    private int imgID;

    public Card (int value, int type, int imgID){
        this.value = value;
        this.type = type;
        this.imgID = imgID;
    }

    public int getValue(){
        return this.value;
    }
    public int getType(){
        return this.type;
    }
    public int getImgID(){
        return this.imgID;
    }

    public String toString(){
        String str = "" + this.value;
        switch (this.type){
            case 0:
                return str + " Espadas";
            case 1:
                return str + " Copas";
            case 2:
                return str + " Bastos";
            case 3:
                return str + " Oros";
        }
        return "";
    }
}
