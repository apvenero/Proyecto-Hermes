package lab.comunicador;


public class Configuracion {
    private String ip;
    private String puerto;
    private int id;

    public Configuracion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Configuracion{" +
                "ip='" + ip + '\'' +
                ", puerto='" + puerto +
                '}';
    }
}
