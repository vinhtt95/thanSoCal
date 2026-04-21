import java.util.LinkedHashMap;
import java.util.Map;

public class CungChucCalculator {

    // Định nghĩa 12 Địa Chi
    public enum DiaChi {
        TY(0, "Tý"), SUU(1, "Sửu"), DAN(2, "Dần"), MAO(3, "Mão"),
        THIN(4, "Thìn"), TI(5, "Tị"), NGO(6, "Ngọ"), MUI(7, "Mùi"),
        THAN(8, "Thân"), DAU(9, "Dậu"), TUAT(10, "Tuất"), HOI(11, "Hợi");

        private final int index;
        private final String ten;

        DiaChi(int index, String ten) {
            this.index = index;
            this.ten = ten;
        }

        public int getIndex() { return index; }
        public String getTen() { return ten; }

        // Helper map index bất kỳ vào vòng 12 cung (Xử lý mượt số âm khi đi lùi)
        public static DiaChi fromIndex(int index) {
            int normalizedIndex = ((index % 12) + 12) % 12;
            for (DiaChi dc : DiaChi.values()) {
                if (dc.getIndex() == normalizedIndex) {
                    return dc;
                }
            }
            return null;
        }
    }

    // Enum định nghĩa 12 Cung chức + số bước nhảy (chiều Dương) tính từ Mệnh
    public enum CungChuc {
        MENH("Mệnh", 0),
        PHU_MAU("Phụ mẫu", 1),
        PHUC_DUC("Phúc đức", 2),
        DIEN_TRACH("Điền trạch", 3),
        QUAN_LOC("Quan lộc", 4),
        NO_BOC("Nô bộc", 5),
        THIEN_DI("Thiên di", 6),
        TAT_ACH("Tật ách", 7),
        TAI_BACH("Tài bạch", 8),
        TU_TUC("Tử tức", 9),
        PHU_THE("Phu thê", 10),
        HUYNH_DE("Huynh đệ", 11);

        private final String ten;
        private final int buocNhay;

        CungChuc(String ten, int buocNhay) {
            this.ten = ten;
            this.buocNhay = buocNhay;
        }

        public String getTen() { return ten; }
        public int getBuocNhay() { return buocNhay; }
    }

    // Class gom nhóm kết quả
    public static class KetQuaAnCung {
        // Dùng LinkedHashMap để giữ đúng thứ tự khi in ra
        public Map<CungChuc, DiaChi> cacCungChuc = new LinkedHashMap<>();
        public DiaChi cungThan;
    }

    /**
     * Hàm tính vị trí các Cung Chức và Cung Thân
     * @param thangSinh: 1 -> 12 (Tháng Âm lịch)
     * @param gioSinh: 1 -> 12 (1 = Tý, 2 = Sửu, ... 9 = Thân...)
     */
    public static KetQuaAnCung anCungChuc(int thangSinh, int gioSinh) {
        KetQuaAnCung ketQua = new KetQuaAnCung();

        int indexDan = DiaChi.DAN.getIndex(); // Bắt đầu từ Dần = 2

        // Step 01: Từ Dần, tiến theo chiều Dương (Tháng - 1) bước -> tìm được X
        int viTriX = indexDan + (thangSinh - 1);

        // Step 02: Từ X, lùi theo chiều Âm (Giờ - 1) bước -> ra Y (Cung Mệnh)
        int viTriMenh = viTriX - (gioSinh);

        // Step 03: An 12 cung chức xoay vòng theo chiều Dương tính từ Y
        for (CungChuc cung : CungChuc.values()) {
            int viTriHienTai = viTriMenh + cung.getBuocNhay();
            ketQua.cacCungChuc.put(cung, DiaChi.fromIndex(viTriHienTai));
        }

        // Step 04 & 05: Tính Cung Thân (Từ X tiến theo chiều Dương Giờ - 1 bước)
        int viTriThan = viTriX + (gioSinh);
        ketQua.cungThan = DiaChi.fromIndex(viTriThan);

        return ketQua;
    }

    public static void main(String[] args) {
        // Test case khớp với dữ liệu test trong sheet của mày: Tháng 8, Giờ 9 (Giờ Thân)
        int thangSinh = 8;
        int gioSinh = 9;

        KetQuaAnCung ketQua = anCungChuc(thangSinh, gioSinh);

        System.out.println("--- KẾT QUẢ AN CUNG CHỨC ---");
        System.out.println("Tháng sinh: " + thangSinh + " | Giờ sinh: " + gioSinh + " (Giờ " + DiaChi.fromIndex(gioSinh).getTen() + ")\n");

        // In 12 cung chức ra màn hình
        for (Map.Entry<CungChuc, DiaChi> entry : ketQua.cacCungChuc.entrySet()) {
            System.out.println(String.format("Cung %-10s an tại: %s", entry.getKey().getTen(), entry.getValue().getTen()));
        }

        System.out.println("\n=> Cung Thân an tại: " + ketQua.cungThan.getTen());
    }
}