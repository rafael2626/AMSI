package modelos;

public class Reserva {

    private int id , andar , pessoas;
    private  boolean minibar , suite;


    public Reserva(int id, int andar, int pessoas, boolean minibar, boolean suite) {

        this.id = id;
        this.andar = andar;
        this.pessoas = pessoas;
        this.minibar = minibar;
        this.suite = suite;

    }



    public boolean getSuite() {
        return suite;
    }
    public void setSuite(Boolean suite) {
        this.suite = suite;
    }



    public boolean getMini_Bar() {
        return  minibar;
    }

    public void setMini_bar(Boolean mini_bar) {
        this.minibar = mini_bar;
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
