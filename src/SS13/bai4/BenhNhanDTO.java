package SS13.bai4;

import java.util.List;

public class BenhNhanDTO {
    private int id;
    private String hoTen;
    private String soGiuong;
    private List<DichVuDTO> dsDichVu;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSoGiuong() { return soGiuong; }
    public void setSoGiuong(String soGiuong) { this.soGiuong = soGiuong; }
    public List<DichVuDTO> getDsDichVu() { return dsDichVu; }
    public void setDsDichVu(List<DichVuDTO> dsDichVu) { this.dsDichVu = dsDichVu; }
}