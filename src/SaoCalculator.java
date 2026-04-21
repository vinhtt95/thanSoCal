import java.util.LinkedHashMap;
import java.util.Map;

public class SaoCalculator {

    // 1. Định nghĩa 12 Địa Chi
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

    // 2. Enum định nghĩa danh sách các Sao để làm Key cho Map
    public enum Sao {
        // Vòng Tử Vi
        TU_VI("Tử Vi"), THIEN_CO("Thiên Cơ"), THAI_DUONG("Thái Dương"),
        VU_KHUC("Vũ Khúc"), THIEN_DONG("Thiên Đồng"), LIEM_TRINH("Liêm Trinh"),

        // Vòng Thiên Phủ
        THIEN_PHU("Thiên Phủ"), THAI_AM("Thái Âm"), THAM_LANG("Tham Lang"),
        CU_MON("Cự Môn"), THIEN_TUONG("Thiên Tướng"), THIEN_LUONG("Thiên Lương"),
        THAT_SAT("Thất Sát"), PHA_QUAN("Phá Quân"),

        // Sao theo tháng
        TA_PHU("Tả Phù"), HUU_BAT("Hữu Bật"), THIEN_HINH("Thiên Hình"),
        THIEN_GIAI("Thiên Giải"), DIA_GIAI("Địa Giải"), THIEN_RIEU_Y("Thiên Riêu - Thiên Y");

        private final String ten;
        Sao(String ten) { this.ten = ten; }
        public String getTen() { return ten; }
    }

    /**
     * Hàm chính tổng hợp an toàn bộ sao dựa trên Vị trí Tử Vi và Tháng sinh
     */
    public static Map<Sao, DiaChi> anToanBoSao(DiaChi viTriTuVi, int thangSinh) {
        Map<Sao, DiaChi> ketQua = new LinkedHashMap<>();
        int buocThang = thangSinh - 1; // Vì tháng 1 bắt đầu ngay tại cung gốc, nên số bước nhảy là tháng - 1

        // ==========================================
        // BƯỚC 4.1: AN VÒNG TỬ VI (Đi nghịch chiều)
        // ==========================================
        int idxTuVi = viTriTuVi.getIndex();
        ketQua.put(Sao.TU_VI, viTriTuVi);
        ketQua.put(Sao.THIEN_CO, DiaChi.fromIndex(idxTuVi - 1));
        // Cách 1 cung -> trừ thêm 2
        ketQua.put(Sao.THAI_DUONG, DiaChi.fromIndex(idxTuVi - 3));
        ketQua.put(Sao.VU_KHUC, DiaChi.fromIndex(idxTuVi - 4));
        ketQua.put(Sao.THIEN_DONG, DiaChi.fromIndex(idxTuVi - 5));
        // Cách 2 cung -> trừ thêm 3
        ketQua.put(Sao.LIEM_TRINH, DiaChi.fromIndex(idxTuVi - 8));

        // ==========================================
        // BƯỚC 4.2: AN VÒNG THIÊN PHỦ (Đi thuận chiều)
        // ==========================================
        // Trục đối xứng Dần(2) - Thân(8) -> (16 - Tử Vi) % 12
        int idxThienPhu = (16 - idxTuVi) % 12;
        ketQua.put(Sao.THIEN_PHU, DiaChi.fromIndex(idxThienPhu));
        ketQua.put(Sao.THAI_AM, DiaChi.fromIndex(idxThienPhu + 1));
        ketQua.put(Sao.THAM_LANG, DiaChi.fromIndex(idxThienPhu + 2));
        ketQua.put(Sao.CU_MON, DiaChi.fromIndex(idxThienPhu + 3));
        ketQua.put(Sao.THIEN_TUONG, DiaChi.fromIndex(idxThienPhu + 4));
        ketQua.put(Sao.THIEN_LUONG, DiaChi.fromIndex(idxThienPhu + 5));
        ketQua.put(Sao.THAT_SAT, DiaChi.fromIndex(idxThienPhu + 6));
        ketQua.put(Sao.PHA_QUAN, DiaChi.fromIndex(idxThienPhu + 7));

        // ==========================================
        // BƯỚC 5: AN SAO THÁNG
        // ==========================================
        // Tả Phù: Khởi Thìn (4) - Đi thuận
        ketQua.put(Sao.TA_PHU, DiaChi.fromIndex(DiaChi.THIN.getIndex() + buocThang));

        // Hữu Bật: Khởi Tuất (10) - Đi nghịch
        ketQua.put(Sao.HUU_BAT, DiaChi.fromIndex(DiaChi.TUAT.getIndex() - buocThang));

        // Thiên Hình: Khởi Dậu (9) - Đi thuận
        ketQua.put(Sao.THIEN_HINH, DiaChi.fromIndex(DiaChi.DAU.getIndex() + buocThang));

        // Thiên Giải: Khởi Thân (8) - Đi thuận
        ketQua.put(Sao.THIEN_GIAI, DiaChi.fromIndex(DiaChi.THAN.getIndex() + buocThang));

        // Địa Giải: Khởi Mùi (7) - Đi thuận
        ketQua.put(Sao.DIA_GIAI, DiaChi.fromIndex(DiaChi.MUI.getIndex() + buocThang));

        // Riêu - Y: Khởi Sửu (1) - Đi thuận
        ketQua.put(Sao.THIEN_RIEU_Y, DiaChi.fromIndex(DiaChi.SUU.getIndex() + buocThang));

        return ketQua;
    }

    public static void main(String[] args) {
        // Giả lập test case: Đã tìm được Tử Vi an tại Tuất (10), sinh tháng 8 âm lịch
        DiaChi viTriTuVi = DiaChi.TUAT;
        int thangSinh = 8;

        Map<Sao, DiaChi> bangSao = anToanBoSao(viTriTuVi, thangSinh);

        System.out.println("--- BẢNG AN SAO ---");
        System.out.println("Vị trí Tử Vi: " + viTriTuVi.getTen() + " | Tháng sinh: " + thangSinh + "\n");

        System.out.println("[ Vòng Chính Tinh - Tử Vi ]");
        System.out.println("Tử Vi an tại: " + bangSao.get(Sao.TU_VI).getTen());
        System.out.println("Thiên Cơ an tại: " + bangSao.get(Sao.THIEN_CO).getTen());
        System.out.println("Thái Dương an tại: " + bangSao.get(Sao.THAI_DUONG).getTen());
        System.out.println("Vũ Khúc an tại: " + bangSao.get(Sao.VU_KHUC).getTen());
        System.out.println("Thiên Đồng an tại: " + bangSao.get(Sao.THIEN_DONG).getTen());
        System.out.println("Liêm Trinh an tại: " + bangSao.get(Sao.LIEM_TRINH).getTen());

        System.out.println("\n[ Vòng Chính Tinh - Thiên Phủ ]");
        System.out.println("Thiên Phủ an tại: " + bangSao.get(Sao.THIEN_PHU).getTen());
        System.out.println("Thái Âm an tại: " + bangSao.get(Sao.THAI_AM).getTen());
        System.out.println("Tham Lang an tại: " + bangSao.get(Sao.THAM_LANG).getTen());
        System.out.println("Cự Môn an tại: " + bangSao.get(Sao.CU_MON).getTen());
        System.out.println("Thiên Tướng an tại: " + bangSao.get(Sao.THIEN_TUONG).getTen());
        System.out.println("Thiên Lương an tại: " + bangSao.get(Sao.THIEN_LUONG).getTen());
        System.out.println("Thất Sát an tại: " + bangSao.get(Sao.THAT_SAT).getTen());
        System.out.println("Phá Quân an tại: " + bangSao.get(Sao.PHA_QUAN).getTen());

        System.out.println("\n[ Các Sao theo Tháng ]");
        System.out.println("Tả Phù an tại: " + bangSao.get(Sao.TA_PHU).getTen());
        System.out.println("Hữu Bật an tại: " + bangSao.get(Sao.HUU_BAT).getTen());
        System.out.println("Thiên Hình an tại: " + bangSao.get(Sao.THIEN_HINH).getTen());
        System.out.println("Thiên Giải an tại: " + bangSao.get(Sao.THIEN_GIAI).getTen());
        System.out.println("Địa Giải an tại: " + bangSao.get(Sao.DIA_GIAI).getTen());
        System.out.println("Thiên Riêu - Thiên Y an tại: " + bangSao.get(Sao.THIEN_RIEU_Y).getTen());
    }
}