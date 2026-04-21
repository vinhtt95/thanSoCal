import java.util.*;

public class TuViSystem {

    // =========================================================================
    // 1. ĐỊNH NGHĨA CÁC ENUM CƠ BẢN
    // =========================================================================

    public enum GioiTinh { NAM, NU }

    public enum ThienCan {
        GIAP(0, "Giáp"), AT(1, "Ất"), BINH(2, "Bính"), DINH(3, "Đinh"), MAU(4, "Mậu"),
        KY(5, "Kỷ"), CANH(6, "Canh"), TAN(7, "Tân"), NHAM(8, "Nhâm"), QUY(9, "Quý");
        private final int index; private final String ten;
        ThienCan(int index, String ten) { this.index = index; this.ten = ten; }
        public int getIndex() { return index; } public String getTen() { return ten; }
    }

    public enum DiaChi {
        TY(0, "Tý"), SUU(1, "Sửu"), DAN(2, "Dần"), MAO(3, "Mão"),
        THIN(4, "Thìn"), TI(5, "Tỵ"), NGO(6, "Ngọ"), MUI(7, "Mùi"),
        THAN(8, "Thân"), DAU(9, "Dậu"), TUAT(10, "Tuất"), HOI(11, "Hợi");
        private final int index; private final String ten;
        DiaChi(int index, String ten) { this.index = index; this.ten = ten; }
        public int getIndex() { return index; } public String getTen() { return ten; }

        public static DiaChi fromIndex(int index) {
            int normalizedIndex = ((index % 12) + 12) % 12;
            for (DiaChi dc : DiaChi.values()) { if (dc.getIndex() == normalizedIndex) return dc; }
            return null;
        }
    }

    public enum Cuc {
        THUY_NHI_CUC(0, 2, "Thủy Nhị Cục"), MOC_TAM_CUC(1, 3, "Mộc Tam Cục"),
        KIM_TU_CUC(2, 4, "Kim Tứ Cục"), THO_NGU_CUC(3, 5, "Thổ Ngũ Cục"),
        HOA_LUC_CUC(4, 6, "Hỏa Lục Cục");
        private final int mapValue; private final int heSoChia; private final String ten;
        Cuc(int mapValue, int heSoChia, String ten) { this.mapValue = mapValue; this.heSoChia = heSoChia; this.ten = ten; }
        public int getHeSoChia() { return heSoChia; } public String getTen() { return ten; }
        public static Cuc fromMapValue(int val) {
            for (Cuc c : values()) { if (c.mapValue == val) return c; }
            return null;
        }
    }

    public enum CungChuc {
        MENH("Mệnh", 0), PHU_MAU("Phụ mẫu", 1), PHUC_DUC("Phúc đức", 2), DIEN_TRACH("Điền trạch", 3),
        QUAN_LOC("Quan lộc", 4), NO_BOC("Nô bộc", 5), THIEN_DI("Thiên di", 6), TAT_ACH("Tật ách", 7),
        TAI_BACH("Tài bạch", 8), TU_TUC("Tử tức", 9), PHU_THE("Phu thê", 10), HUYNH_DE("Huynh đệ", 11);
        private final String ten; private final int buocNhay;
        CungChuc(String ten, int buocNhay) { this.ten = ten; this.buocNhay = buocNhay; }
        public String getTen() { return ten; } public int getBuocNhay() { return buocNhay; }
    }

    public enum Sao {
        TU_VI("Tử Vi"), THIEN_CO("Thiên Cơ"), THAI_DUONG("Thái Dương"), VU_KHUC("Vũ Khúc"), THIEN_DONG("Thiên Đồng"), LIEM_TRINH("Liêm Trinh"),
        THIEN_PHU("Thiên Phủ"), THAI_AM("Thái Âm"), THAM_LANG("Tham Lang"), CU_MON("Cự Môn"), THIEN_TUONG("Thiên Tướng"), THIEN_LUONG("Thiên Lương"), THAT_SAT("Thất Sát"), PHA_QUAN("Phá Quân"),
        TA_PHU("Tả Phù"), HUU_BAT("Hữu Bật"), THIEN_HINH("Thiên Hình"), THIEN_GIAI("Thiên Giải"), DIA_GIAI("Địa Giải"), THIEN_RIEU_Y("Thiên Riêu - Y"),
        VAN_KHUC("Văn Khúc"), VAN_XUONG("Văn Xương"), DIA_KIEP("Địa Kiếp"), DIA_KHONG("Địa Không"),
        THAI_PHU("Thai Phụ"), PHONG_CAO("Phong Cáo"), HOA_TINH("Hỏa Tinh"), LINH_TINH("Linh Tinh");
        private final String ten; Sao(String ten) { this.ten = ten; } public String getTen() { return ten; }
    }

    // =========================================================================
    // 2. CẤU TRÚC DỮ LIỆU LÁ SỐ
    // =========================================================================

    public static class CungDiaBan {
        public DiaChi diaChi;
        public List<CungChuc> cacCungChuc = new ArrayList<>();
        public List<Sao> cacSao = new ArrayList<>();
        public boolean laCungThan = false;

        public CungDiaBan(DiaChi diaChi) { this.diaChi = diaChi; }
    }

    public static class LaSo {
        public Map<DiaChi, CungDiaBan> diaBan = new LinkedHashMap<>();
        public Cuc cuc;
        public boolean amDuongThuanLy;

        public LaSo() {
            for (DiaChi dc : DiaChi.values()) {
                diaBan.put(dc, new CungDiaBan(dc));
            }
        }
        public void themSao(Sao sao, DiaChi viTri) {
            diaBan.get(viTri).cacSao.add(sao);
        }
    }

    private static final int[][] MATRIX_CUC = {
            {0, 4, 3, 1, 2, 0, 4, 3, 1, 2}, {0, 4, 3, 1, 2, 0, 4, 3, 1, 2},
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, {4, 3, 1, 2, 0, 4, 3, 1, 2, 0},
            {1, 2, 0, 4, 3, 1, 2, 0, 4, 3}, {1, 2, 0, 4, 3, 1, 2, 0, 4, 3},
            {3, 1, 2, 0, 4, 3, 1, 2, 0, 4}, {3, 1, 2, 0, 4, 3, 1, 2, 0, 4},
            {2, 0, 4, 3, 1, 2, 0, 4, 3, 1}, {2, 0, 4, 3, 1, 2, 0, 4, 3, 1},
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}
    };

    // =========================================================================
    // 3. HÀM CORE: BỔ SUNG chiNamSinh ĐỂ TÍNH HỎA TINH, LINH TINH
    // =========================================================================

    public static LaSo lapLaSo(ThienCan canNamSinh, DiaChi chiNamSinh, GioiTinh gioiTinh, int thangSinh, int ngaySinh, DiaChi gioSinh) {
        LaSo laSo = new LaSo();
        int buocGio = gioSinh.getIndex();

        // Xét Âm Dương Thuận Lý / Nghịch Lý
        boolean laCanDuong = (canNamSinh.getIndex() % 2 == 0);
        laSo.amDuongThuanLy = (gioiTinh == GioiTinh.NAM && laCanDuong) || (gioiTinh == GioiTinh.NU && !laCanDuong);

        // BƯỚC 1 & 2: MỆNH VÀ CUNG CHỨC
        int viTriX = DiaChi.DAN.getIndex() + (thangSinh - 1);
        int viTriMenh = viTriX - buocGio;
        DiaChi cungMenh = DiaChi.fromIndex(viTriMenh);

        for (CungChuc cc : CungChuc.values()) {
            laSo.diaBan.get(DiaChi.fromIndex(viTriMenh + cc.getBuocNhay())).cacCungChuc.add(cc);
        }
        laSo.diaBan.get(DiaChi.fromIndex(viTriX + buocGio)).laCungThan = true;

        // BƯỚC 3: TÌM CỤC
        int cucMapValue = MATRIX_CUC[cungMenh.getIndex()][canNamSinh.getIndex()];
        laSo.cuc = Cuc.fromMapValue(cucMapValue);

        // BƯỚC 4: TỬ VI & THIÊN PHỦ
        int heSoCuc = laSo.cuc.getHeSoChia();
        int phanDu = ngaySinh % heSoCuc;
        int muon = (phanDu != 0) ? (heSoCuc - phanDu) : 0;
        int thuong = (ngaySinh + muon) / heSoCuc;
        int viTriBanDauTuVi = DiaChi.DAN.getIndex() + (thuong - 1);
        int viTriTuVi = (muon % 2 == 0) ? (viTriBanDauTuVi + muon) : (viTriBanDauTuVi - muon);

        int idxTuVi = DiaChi.fromIndex(viTriTuVi).getIndex();

        laSo.themSao(Sao.TU_VI, DiaChi.fromIndex(idxTuVi));
        laSo.themSao(Sao.THIEN_CO, DiaChi.fromIndex(idxTuVi - 1));
        laSo.themSao(Sao.THAI_DUONG, DiaChi.fromIndex(idxTuVi - 3));
        laSo.themSao(Sao.VU_KHUC, DiaChi.fromIndex(idxTuVi - 4));
        laSo.themSao(Sao.THIEN_DONG, DiaChi.fromIndex(idxTuVi - 5));
        laSo.themSao(Sao.LIEM_TRINH, DiaChi.fromIndex(idxTuVi - 8));

        int idxThienPhu = (16 - idxTuVi) % 12;
        laSo.themSao(Sao.THIEN_PHU, DiaChi.fromIndex(idxThienPhu));
        laSo.themSao(Sao.THAI_AM, DiaChi.fromIndex(idxThienPhu + 1));
        laSo.themSao(Sao.THAM_LANG, DiaChi.fromIndex(idxThienPhu + 2));
        laSo.themSao(Sao.CU_MON, DiaChi.fromIndex(idxThienPhu + 3));
        laSo.themSao(Sao.THIEN_TUONG, DiaChi.fromIndex(idxThienPhu + 4));
        laSo.themSao(Sao.THIEN_LUONG, DiaChi.fromIndex(idxThienPhu + 5));
        laSo.themSao(Sao.THAT_SAT, DiaChi.fromIndex(idxThienPhu + 6));
        laSo.themSao(Sao.PHA_QUAN, DiaChi.fromIndex(idxThienPhu + 7));

        // BƯỚC 5: SAO THÁNG
        int buocThang = thangSinh - 1;
        laSo.themSao(Sao.TA_PHU, DiaChi.fromIndex(DiaChi.THIN.getIndex() + buocThang));
        laSo.themSao(Sao.HUU_BAT, DiaChi.fromIndex(DiaChi.TUAT.getIndex() - buocThang));
        laSo.themSao(Sao.THIEN_HINH, DiaChi.fromIndex(DiaChi.DAU.getIndex() + buocThang));
        laSo.themSao(Sao.THIEN_GIAI, DiaChi.fromIndex(DiaChi.THAN.getIndex() + buocThang));
        laSo.themSao(Sao.DIA_GIAI, DiaChi.fromIndex(DiaChi.MUI.getIndex() + buocThang));
        laSo.themSao(Sao.THIEN_RIEU_Y, DiaChi.fromIndex(DiaChi.SUU.getIndex() + buocThang));

        // BƯỚC 6: SAO GIỜ (Văn Khúc, Văn Xương, Địa Kiếp, Địa Không, Thai Phụ, Phong Cáo)
        laSo.themSao(Sao.VAN_KHUC, DiaChi.fromIndex(DiaChi.THIN.getIndex() + buocGio));
        laSo.themSao(Sao.VAN_XUONG, DiaChi.fromIndex(DiaChi.TUAT.getIndex() - buocGio));
        laSo.themSao(Sao.DIA_KIEP, DiaChi.fromIndex(DiaChi.HOI.getIndex() + buocGio));
        laSo.themSao(Sao.DIA_KHONG, DiaChi.fromIndex(DiaChi.HOI.getIndex() - buocGio));
        laSo.themSao(Sao.THAI_PHU, DiaChi.fromIndex(DiaChi.DAN.getIndex() + buocGio));
        laSo.themSao(Sao.PHONG_CAO, DiaChi.fromIndex(DiaChi.NGO.getIndex() + buocGio));

        // ==========================================
        // XỬ LÝ RIÊNG HỎA TINH & LINH TINH
        // ==========================================
        int khoiHoaTinhIndex = 0;
        int khoiLinhTinhIndex = 0;

        // 1. Tìm cung khởi dựa theo Tam Hợp Địa Chi Năm Sinh
        switch (chiNamSinh) {
            case DAN: case NGO: case TUAT:
                khoiHoaTinhIndex = DiaChi.SUU.getIndex();
                khoiLinhTinhIndex = DiaChi.MAO.getIndex();
                break;
            case THAN: case TY: case THIN:
                khoiHoaTinhIndex = DiaChi.DAN.getIndex();
                khoiLinhTinhIndex = DiaChi.TUAT.getIndex();
                break;
            case TI: case DAU: case SUU: // TỊ
                khoiHoaTinhIndex = DiaChi.MAO.getIndex();
                khoiLinhTinhIndex = DiaChi.TUAT.getIndex();
                break;
            case HOI: case MAO: case MUI:
                khoiHoaTinhIndex = DiaChi.DAU.getIndex();
                khoiLinhTinhIndex = DiaChi.TUAT.getIndex();
                break;
        }

        // 2. Tịnh tiến thuận nghịch theo Âm Dương của lá số
        if (laSo.amDuongThuanLy) {
            // Âm Dương Thuận Lý: Hỏa đi thuận, Linh đi nghịch
            laSo.themSao(Sao.HOA_TINH, DiaChi.fromIndex(khoiHoaTinhIndex + buocGio));
            laSo.themSao(Sao.LINH_TINH, DiaChi.fromIndex(khoiLinhTinhIndex - buocGio));
        } else {
            // Âm Dương Nghịch Lý: Hỏa đi nghịch, Linh đi thuận
            laSo.themSao(Sao.HOA_TINH, DiaChi.fromIndex(khoiHoaTinhIndex - buocGio));
            laSo.themSao(Sao.LINH_TINH, DiaChi.fromIndex(khoiLinhTinhIndex + buocGio));
        }

        return laSo;
    }

    // =========================================================================
    // 4. TEST LẤY OUTPUT
    // =========================================================================
    public static void main(String[] args) {
        // Giả lập test case: Sinh năm Ất Sửu (1985), Nam mạng
        ThienCan canNamSinh = ThienCan.AT;
        DiaChi chiNamSinh = DiaChi.HOI; // Đã thêm tuổi vào đây
        GioiTinh gioiTinh = GioiTinh.NAM;

        int thangSinh = 8;
        int ngaySinh = 29;
        DiaChi gioSinh = DiaChi.DAU;

        LaSo laSo = lapLaSo(canNamSinh, chiNamSinh, gioiTinh, thangSinh, ngaySinh, gioSinh);

        System.out.println("=== THÔNG TIN BẢN MỆNH ===");
        System.out.println("Năm: " + canNamSinh.getTen() + " " + chiNamSinh.getTen() +
                " | Giới tính: " + gioiTinh.name());
        System.out.println("Âm Dương: " + (laSo.amDuongThuanLy ? "Thuận Lý" : "Nghịch Lý"));
        System.out.println("Tháng: " + thangSinh + " | Ngày: " + ngaySinh + " | Giờ: " + gioSinh.getTen());
        System.out.println("Cục: " + laSo.cuc.getTen() + "\n");

        System.out.println("=== BẢN ĐỒ 12 CUNG LÁ SỐ ===");
        for (DiaChi dc : DiaChi.values()) {
            CungDiaBan cung = laSo.diaBan.get(dc);

            List<String> tenCungChuc = new ArrayList<>();
            for (CungChuc cc : cung.cacCungChuc) tenCungChuc.add(cc.getTen());
            if (cung.laCungThan) tenCungChuc.add("Thân");

            List<String> tenSao = new ArrayList<>();
            for (Sao s : cung.cacSao) tenSao.add(s.getTen());

            System.out.printf("[ Cung %-4s ] - Chức: %-25s | Sao: %s\n",
                    dc.getTen(),
                    String.join(", ", tenCungChuc),
                    String.join(", ", tenSao));
        }
    }
}