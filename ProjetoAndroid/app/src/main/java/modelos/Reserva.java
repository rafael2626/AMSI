package modelos;

public class Reserva {

    private int id , andar , pessoas;
    private  boolean mini_bar , suite;


    public Reserva(int id, int andar, int pessoas, boolean mini_bar, boolean suite) {

        this.id = id;
        this.andar = andar;
        this.pessoas = pessoas;
        this.mini_bar = mini_bar;
        this.suite = suite;

    }



    public boolean getSuite() {
        return suite;
    }
    public void setSuite(Boolean suite) {
        this.suite = suite;
    }



    public boolean getMini_Bar() {
        return  mini_bar;
    }

    public void setMini_bar(Boolean mini_bar) {
        this.mini_bar = mini_bar;
    }


    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getPessoas() {
        return pessoas;
    }

    public void setPessoas(int pessoas) {
        this.pessoas = pessoas;
    }

}
