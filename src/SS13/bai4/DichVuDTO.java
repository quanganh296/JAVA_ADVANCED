package SS13.bai4;

import java.sql.Timestamp;

public class DichVuDTO {
    private int id;
    private String tenDichVu;
    private Timestamp gioDung;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }
    public Timestamp getGioDung() { return gioDung; }
    public void setGioDung(Timestamp gioDung) { this.gioDung = gioDung; }
}