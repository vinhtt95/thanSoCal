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
        THAI_PHU("Thai Phụ"), PHONG_CAO("Phong Cáo"), HOA_TINH("Hỏa Tinh"), LINH_TINH("Linh Tinh"),
        TAM_THAI("Tam Thai"), BAT_TOA("Bát Tọa"), AN_QUANG("Ân Quang"), THIEN_QUY("Thiên Quý"),
        LOC_TON("Lộc Tồn"), BAC_SI("Bác Sĩ"), LUC_SI("Lực Sĩ"), THANH_LONG("Thanh Long"), TIEU_HAO("Tiểu Hao"), TUONG_QUAN("Tướng Quân"),
        TAU_THU("Tấu Thư"), PHI_LIEM("Phi Liêm"), HI_THAN("Hỷ Thần"), BENH_PHU("Bệnh Phù"), DAI_HAO("Đại Hao"), PHUC_BINH("Phục Binh"), QUAN_PHU("Quan Phủ"),
        LUU_NIEN_VAN_TINH("Lưu Niên Văn Tinh"), DUONG_PHU("Đường Phù"), QUOC_AN("Quốc Ấn"),
        LUU_HA("Lưu Hà"), THIEN_TRU("Thiên Trù"), THIEN_QUAN("Thiên Quan"), THIEN_PHUC("Thiên Phúc"), THIEN_KHOI("Thiên Khôi"), THIEN_VIET("Thiên Việt"),
        THAI_TUE("Thái Tuế"), THIEU_DUONG("Thiếu Dương"), TANG_MON("Tang Môn"), THIEU_AM("Thiếu Âm"),
        QUAN_PHU_T("Quan Phù"), TU_PHU("Tử Phù"), TUE_PHA("Tuế Phá"), LONG_DUC("Long Đức"),
        BACH_HO("Bạch Hổ"), PHUC_DUC("Phúc Đức"), DIEU_KHACH("Điếu Khách"), TRUC_PHU("Trực Phù"),
        THIEN_MA("Thiên Mã"), DAO_HOA("Đào Hoa"), HOA_CAI("Hoa Cái"), KIEP_SAT("Kiếp Sát"),
        PHA_TOAI("Phá Toái"), CO_THAN("Cô Thần"), QUA_TU("Quả Tú"), THIEN_KHOC("Thiên Khốc"),
        THIEN_HU("Thiên Hư"), THIEN_HY("Thiên Hỷ"), HONG_LOAN("Hồng Loan"),
        LONG_TRI("Long Trì"), PHUONG_CAC("Phượng Các"), GIAI_THAN("Giải Thần"),
        TRUONG_SINH("Trường Sinh"), MOC_DUC("Mộc Dục"), QUAN_DOI("Quan Đới"), LAM_QUAN("Lâm Quan"),
        DE_VUONG("Đế Vượng"), SUY("Suy"), BENH("Bệnh"), TU("Tử"), MO("Mộ"), TUYET("Tuyệt"), THAI("Thai"), DUONG("Dưỡng"),
        TUAN_KHONG("Tuần Không"), TRIET_LO("Triệt Lộ"),
        HOA_LOC("Hóa Lộc"), HOA_QUYEN("Hóa Quyền"), HOA_KHOA("Hóa Khoa"), HOA_KY("Hóa Kỵ"),

        // BƯỚC 13: CÁC SAO CÒN LẠI
        THIEN_LA("Thiên La"), DIA_VONG("Địa Võng"), THIEN_THUONG("Thiên Thương"),
        THIEN_SU("Thiên Sứ"), THIEN_TAI("Thiên Tài"), THIEN_THO("Thiên Thọ");

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
            for (DiaChi dc : DiaChi.values()) { diaBan.put(dc, new CungDiaBan(dc)); }
        }
        public void themSao(Sao sao, DiaChi viTri) { diaBan.get(viTri).cacSao.add(sao); }
    }

    private static final int[][] MATRIX_CUC = {
            {0, 4, 3, 1, 2, 0, 4, 3, 1, 2}, {0, 4, 3, 1, 2, 0, 4, 3, 1, 2},
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, {4, 3, 1, 2, 0, 4, 3, 1, 2, 0},
            {1, 2, 0, 4, 3, 1, 2, 0, 4, 3}, {1, 2, 0, 4, 3, 1, 2, 0, 4, 3},
            {3, 1, 2, 0, 4, 3, 1, 2, 0, 4}, {3, 1, 2, 0, 4, 3, 1, 2, 0, 4},
            {2, 0, 4, 3, 1, 2, 0, 4, 3, 1}, {2, 0, 4, 3, 1, 2, 0, 4, 3, 1},
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}
    };

    private static final int[] MAP_LOC_TON = {2, 3, 5, 6, 5, 6, 8, 9, 11, 0};
    private static final int[] MAP_LUU_HA = {9, 10, 7, 8, 5, 6, 3, 4, 11, 2};
    private static final int[] MAP_THIEN_TRU = {5, 6, 0, 5, 6, 8, 2, 6, 9, 10};
    private static final int[] MAP_THIEN_QUAN = {7, 4, 5, 2, 3, 9, 11, 9, 10, 6};
    private static final int[] MAP_THIEN_PHUC = {9, 8, 0, 11, 3, 2, 6, 5, 6, 5};
    private static final int[] MAP_THIEN_KHOI = {1, 0, 11, 11, 1, 0, 6, 6, 3, 3};
    private static final int[] MAP_THIEN_VIET = {7, 8, 9, 9, 7, 8, 2, 2, 5, 5};
    private static final int[] MAP_THIEN_MA = {2, 11, 8, 5, 2, 11, 8, 5, 2, 11, 8, 5};
    private static final int[] MAP_DAO_HOA = {9, 6, 3, 0, 9, 6, 3, 0, 9, 6, 3, 0};
    private static final int[] MAP_HOA_CAI = {4, 1, 10, 7, 4, 1, 10, 7, 4, 1, 10, 7};
    private static final int[] MAP_KIEP_SAT = {5, 2, 11, 8, 5, 2, 11, 8, 5, 2, 11, 8};
    private static final int[] MAP_PHA_TOAI = {5, 1, 9, 5, 1, 9, 5, 1, 9, 5, 1, 9};
    private static final int[] MAP_CO_THAN = {2, 2, 5, 5, 5, 8, 8, 8, 11, 11, 11, 2};

    private static final Sao[][] MAP_TU_HOA = {
            {Sao.LIEM_TRINH, Sao.PHA_QUAN, Sao.VU_KHUC, Sao.THAI_DUONG}, // Giáp
            {Sao.THIEN_CO, Sao.THIEN_LUONG, Sao.TU_VI, Sao.THAI_AM},     // Ất
            {Sao.THIEN_DONG, Sao.THIEN_CO, Sao.VAN_XUONG, Sao.LIEM_TRINH}, // Bính
            {Sao.THAI_AM, Sao.THIEN_DONG, Sao.THIEN_CO, Sao.CU_MON},     // Đinh
            {Sao.THAM_LANG, Sao.THAI_AM, Sao.HUU_BAT, Sao.THIEN_CO},     // Mậu
            {Sao.VU_KHUC, Sao.THAM_LANG, Sao.THIEN_LUONG, Sao.VAN_KHUC}, // Kỷ
            {Sao.THAI_DUONG, Sao.VU_KHUC, Sao.THAI_AM, Sao.THIEN_DONG},  // Canh
            {Sao.CU_MON, Sao.THAI_DUONG, Sao.VAN_KHUC, Sao.VAN_XUONG},   // Tân
            {Sao.THIEN_LUONG, Sao.TU_VI, Sao.TA_PHU, Sao.VU_KHUC},       // Nhâm
            {Sao.PHA_QUAN, Sao.CU_MON, Sao.THAI_AM, Sao.THAM_LANG}       // Quý
    };

    // =========================================================================
    // 3. HÀM CORE LẬP LÁ SỐ
    // =========================================================================

    public static LaSo lapLaSo(ThienCan canNamSinh, DiaChi chiNamSinh, GioiTinh gioiTinh, int thangSinh, int ngaySinh, DiaChi gioSinh) {
        LaSo laSo = new LaSo();
        int buocGio = gioSinh.getIndex();
        int buocThang = thangSinh - 1;

        int canIndex = canNamSinh.getIndex();
        int chiIndex = chiNamSinh.getIndex();
        boolean laCanDuong = (canIndex % 2 == 0);
        laSo.amDuongThuanLy = (gioiTinh == GioiTinh.NAM && laCanDuong) || (gioiTinh == GioiTinh.NU && !laCanDuong);

        int viTriX = DiaChi.DAN.getIndex() + buocThang;
        int viTriMenh = viTriX - buocGio;
        DiaChi cungMenh = DiaChi.fromIndex(viTriMenh);

        for (CungChuc cc : CungChuc.values()) { laSo.diaBan.get(DiaChi.fromIndex(viTriMenh + cc.getBuocNhay())).cacCungChuc.add(cc); }

        int viTriThan = (viTriX + buocGio) % 12; // Lưu biến viTriThan để dùng cho Bước 13
        laSo.diaBan.get(DiaChi.fromIndex(viTriThan)).laCungThan = true;

        laSo.cuc = Cuc.fromMapValue(MATRIX_CUC[cungMenh.getIndex()][canIndex]);

        int heSoCuc = laSo.cuc.getHeSoChia();
        int phanDu = ngaySinh % heSoCuc;
        int muon = (phanDu != 0) ? (heSoCuc - phanDu) : 0;
        int thuong = (ngaySinh + muon) / heSoCuc;
        int viTriTuVi = DiaChi.DAN.getIndex() + (thuong - 1) + ((muon % 2 == 0) ? muon : -muon);

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

        int idxTaPhu = DiaChi.THIN.getIndex() + buocThang;
        int idxHuuBat = DiaChi.TUAT.getIndex() - buocThang;
        laSo.themSao(Sao.TA_PHU, DiaChi.fromIndex(idxTaPhu));
        laSo.themSao(Sao.HUU_BAT, DiaChi.fromIndex(idxHuuBat));
        laSo.themSao(Sao.THIEN_HINH, DiaChi.fromIndex(DiaChi.DAU.getIndex() + buocThang));
        laSo.themSao(Sao.THIEN_GIAI, DiaChi.fromIndex(DiaChi.THAN.getIndex() + buocThang));
        laSo.themSao(Sao.DIA_GIAI, DiaChi.fromIndex(DiaChi.MUI.getIndex() + buocThang));
        laSo.themSao(Sao.THIEN_RIEU_Y, DiaChi.fromIndex(DiaChi.SUU.getIndex() + buocThang));

        int idxVanKhuc = DiaChi.THIN.getIndex() + buocGio;
        int idxVanXuong = DiaChi.TUAT.getIndex() - buocGio;
        laSo.themSao(Sao.VAN_KHUC, DiaChi.fromIndex(idxVanKhuc));
        laSo.themSao(Sao.VAN_XUONG, DiaChi.fromIndex(idxVanXuong));
        laSo.themSao(Sao.DIA_KIEP, DiaChi.fromIndex(DiaChi.HOI.getIndex() + buocGio));
        laSo.themSao(Sao.DIA_KHONG, DiaChi.fromIndex(DiaChi.HOI.getIndex() - buocGio));
        laSo.themSao(Sao.THAI_PHU, DiaChi.fromIndex(DiaChi.DAN.getIndex() + buocGio));
        laSo.themSao(Sao.PHONG_CAO, DiaChi.fromIndex(DiaChi.NGO.getIndex() + buocGio));

        int khoiHoaTinh = 0, khoiLinhTinh = 0;
        switch (chiNamSinh) {
            case DAN: case NGO: case TUAT: khoiHoaTinh = DiaChi.SUU.getIndex(); khoiLinhTinh = DiaChi.MAO.getIndex(); break;
            case THAN: case TY: case THIN: khoiHoaTinh = DiaChi.DAN.getIndex(); khoiLinhTinh = DiaChi.TUAT.getIndex(); break;
            case TI: case DAU: case SUU:   khoiHoaTinh = DiaChi.MAO.getIndex(); khoiLinhTinh = DiaChi.TUAT.getIndex(); break;
            case HOI: case MAO: case MUI:  khoiHoaTinh = DiaChi.DAU.getIndex(); khoiLinhTinh = DiaChi.TUAT.getIndex(); break;
        }
        laSo.themSao(Sao.HOA_TINH, DiaChi.fromIndex(laSo.amDuongThuanLy ? khoiHoaTinh + buocGio : khoiHoaTinh - buocGio));
        laSo.themSao(Sao.LINH_TINH, DiaChi.fromIndex(laSo.amDuongThuanLy ? khoiLinhTinh - buocGio : khoiLinhTinh + buocGio));

        int buocNgay = ngaySinh - 1;
        int buocNgayTru1 = ngaySinh - 2;
        laSo.themSao(Sao.TAM_THAI, DiaChi.fromIndex(idxTaPhu + buocNgay));
        laSo.themSao(Sao.BAT_TOA, DiaChi.fromIndex(idxHuuBat - buocNgay));
        laSo.themSao(Sao.AN_QUANG, DiaChi.fromIndex(idxVanXuong + buocNgayTru1));
        laSo.themSao(Sao.THIEN_QUY, DiaChi.fromIndex(idxVanKhuc - buocNgayTru1));

        int idxLocTon = MAP_LOC_TON[canIndex];
        laSo.themSao(Sao.LOC_TON, DiaChi.fromIndex(idxLocTon));
        Sao[] vongBacSi = {
                Sao.BAC_SI, Sao.LUC_SI, Sao.THANH_LONG, Sao.TIEU_HAO, Sao.TUONG_QUAN, Sao.TAU_THU,
                Sao.PHI_LIEM, Sao.HI_THAN, Sao.BENH_PHU, Sao.DAI_HAO, Sao.PHUC_BINH, Sao.QUAN_PHU
        };
        for (int i = 0; i < 12; i++) {
            laSo.themSao(vongBacSi[i], DiaChi.fromIndex(idxLocTon + (laSo.amDuongThuanLy ? i : -i)));
        }

        laSo.themSao(Sao.LUU_NIEN_VAN_TINH, DiaChi.fromIndex(idxLocTon + 3));
        laSo.themSao(Sao.DUONG_PHU, DiaChi.fromIndex(idxLocTon + 5));
        laSo.themSao(Sao.QUOC_AN, DiaChi.fromIndex(idxLocTon - 4));
        laSo.themSao(Sao.LUU_HA, DiaChi.fromIndex(MAP_LUU_HA[canIndex]));
        laSo.themSao(Sao.THIEN_TRU, DiaChi.fromIndex(MAP_THIEN_TRU[canIndex]));
        laSo.themSao(Sao.THIEN_QUAN, DiaChi.fromIndex(MAP_THIEN_QUAN[canIndex]));
        laSo.themSao(Sao.THIEN_PHUC, DiaChi.fromIndex(MAP_THIEN_PHUC[canIndex]));
        laSo.themSao(Sao.THIEN_KHOI, DiaChi.fromIndex(MAP_THIEN_KHOI[canIndex]));
        laSo.themSao(Sao.THIEN_VIET, DiaChi.fromIndex(MAP_THIEN_VIET[canIndex]));

        Sao[] vongThaiTue = {
                Sao.THAI_TUE, Sao.THIEU_DUONG, Sao.TANG_MON, Sao.THIEU_AM, Sao.QUAN_PHU_T, Sao.TU_PHU,
                Sao.TUE_PHA, Sao.LONG_DUC, Sao.BACH_HO, Sao.PHUC_DUC, Sao.DIEU_KHACH, Sao.TRUC_PHU
        };
        for (int i = 0; i < 12; i++) { laSo.themSao(vongThaiTue[i], DiaChi.fromIndex(chiIndex + i)); }

        laSo.themSao(Sao.THIEN_MA, DiaChi.fromIndex(MAP_THIEN_MA[chiIndex]));
        laSo.themSao(Sao.DAO_HOA, DiaChi.fromIndex(MAP_DAO_HOA[chiIndex]));
        laSo.themSao(Sao.HOA_CAI, DiaChi.fromIndex(MAP_HOA_CAI[chiIndex]));
        laSo.themSao(Sao.KIEP_SAT, DiaChi.fromIndex(MAP_KIEP_SAT[chiIndex]));
        laSo.themSao(Sao.PHA_TOAI, DiaChi.fromIndex(MAP_PHA_TOAI[chiIndex]));
        laSo.themSao(Sao.CO_THAN, DiaChi.fromIndex(MAP_CO_THAN[chiIndex]));

        int idxQuaTu = (MAP_CO_THAN[chiIndex] - 4 + 12) % 12;
        laSo.themSao(Sao.QUA_TU, DiaChi.fromIndex(idxQuaTu));
        laSo.themSao(Sao.HONG_LOAN, DiaChi.fromIndex((DiaChi.MAO.getIndex() - chiIndex + 12) % 12));
        laSo.themSao(Sao.THIEN_HY, DiaChi.fromIndex((DiaChi.DAU.getIndex() - chiIndex + 12) % 12));
        laSo.themSao(Sao.THIEN_KHOC, DiaChi.fromIndex((DiaChi.NGO.getIndex() - chiIndex + 12) % 12));
        laSo.themSao(Sao.THIEN_HU, DiaChi.fromIndex((DiaChi.NGO.getIndex() + chiIndex) % 12));

        int idxPhuongCac = (DiaChi.TUAT.getIndex() - chiIndex + 12) % 12;
        laSo.themSao(Sao.LONG_TRI, DiaChi.fromIndex((DiaChi.THIN.getIndex() + chiIndex) % 12));
        laSo.themSao(Sao.PHUONG_CAC, DiaChi.fromIndex(idxPhuongCac));
        laSo.themSao(Sao.GIAI_THAN, DiaChi.fromIndex(idxPhuongCac));

        int khoiTruongSinh = 0;
        switch (laSo.cuc) {
            case THUY_NHI_CUC: case THO_NGU_CUC: khoiTruongSinh = DiaChi.THAN.getIndex(); break;
            case MOC_TAM_CUC: khoiTruongSinh = DiaChi.HOI.getIndex(); break;
            case KIM_TU_CUC: khoiTruongSinh = DiaChi.TI.getIndex(); break;
            case HOA_LUC_CUC: khoiTruongSinh = DiaChi.DAN.getIndex(); break;
        }

        Sao[] vongTruongSinh = {
                Sao.TRUONG_SINH, Sao.MOC_DUC, Sao.QUAN_DOI, Sao.LAM_QUAN, Sao.DE_VUONG,
                Sao.SUY, Sao.BENH, Sao.TU, Sao.MO, Sao.TUYET, Sao.THAI, Sao.DUONG
        };
        for (int i = 0; i < 12; i++) {
            laSo.themSao(vongTruongSinh[i], DiaChi.fromIndex(khoiTruongSinh + (laSo.amDuongThuanLy ? i : -i)));
        }

        // ==========================================
        // BƯỚC 10: AN SAO TRIỆT
        // ==========================================
        int idxTriet1 = (8 - 2 * (canIndex % 5) + 12) % 12;
        laSo.themSao(Sao.TRIET_LO, DiaChi.fromIndex(idxTriet1));
        laSo.themSao(Sao.TRIET_LO, DiaChi.fromIndex(idxTriet1 + 1));

        // ==========================================
        // BƯỚC 11: AN SAO TUẦN
        // ==========================================
        int baseTuan = (chiIndex - canIndex + 12) % 12;
        laSo.themSao(Sao.TUAN_KHONG, DiaChi.fromIndex(baseTuan + 10));
        laSo.themSao(Sao.TUAN_KHONG, DiaChi.fromIndex(baseTuan + 11));

        // ==========================================
        // BƯỚC 12: AN SAO TỨ HÓA (Lộc - Quyền - Khoa - Kỵ)
        // ==========================================
        Sao[] listSaoGocTuHoa = MAP_TU_HOA[canIndex];
        Sao[] listSaoTuHoa = {Sao.HOA_LOC, Sao.HOA_QUYEN, Sao.HOA_KHOA, Sao.HOA_KY};

        for (int i = 0; i < 4; i++) {
            Sao saoGoc = listSaoGocTuHoa[i];
            for (DiaChi dc : DiaChi.values()) {
                if (laSo.diaBan.get(dc).cacSao.contains(saoGoc)) {
                    laSo.themSao(listSaoTuHoa[i], dc);
                    break;
                }
            }
        }

        // ==========================================
        // BƯỚC 13: CÁC SAO CÒN LẠI (Thiên La, Địa Võng, Thương, Sứ, Tài, Thọ)
        // ==========================================
        laSo.themSao(Sao.THIEN_LA, DiaChi.THIN);
        laSo.themSao(Sao.DIA_VONG, DiaChi.TUAT);

        // Thiên Thương ở cung Tật Ách
        int idxTatAch = (viTriMenh + CungChuc.TAT_ACH.getBuocNhay()) % 12;
        laSo.themSao(Sao.THIEN_THUONG, DiaChi.fromIndex(idxTatAch));

        // Thiên Sứ ở cung Nô Bộc
        int idxNoBoc = (viTriMenh + CungChuc.NO_BOC.getBuocNhay()) % 12;
        laSo.themSao(Sao.THIEN_SU, DiaChi.fromIndex(idxNoBoc));

        // Thiên Tài: Từ cung Mệnh đếm thuận đến năm sinh
        int idxThienTai = (viTriMenh + chiIndex) % 12;
        laSo.themSao(Sao.THIEN_TAI, DiaChi.fromIndex(idxThienTai));

        // Thiên Thọ: Từ cung Thân đếm thuận đến năm sinh
        int idxThienTho = (viTriThan + chiIndex) % 12;
        laSo.themSao(Sao.THIEN_THO, DiaChi.fromIndex(idxThienTho));

        return laSo;
    }

    // =========================================================================
    // 4. TEST LẤY OUTPUT
    // =========================================================================
    public static void main(String[] args) {
        ThienCan canNamSinh = ThienCan.AT;
        DiaChi chiNamSinh = DiaChi.HOI; // Đã thêm tuổi vào đây
        GioiTinh gioiTinh = GioiTinh.NAM;

        int thangSinh = 8;
        int ngaySinh = 29;
        DiaChi gioSinh = DiaChi.DAU;

        LaSo laSo = lapLaSo(canNamSinh, chiNamSinh, gioiTinh, thangSinh, ngaySinh, gioSinh);

        System.out.println("=== THÔNG TIN BẢN MỆNH ===");
        System.out.println("Năm: " + canNamSinh.getTen() + " " + chiNamSinh.getTen() + " | Giới tính: " + gioiTinh.name());
        System.out.println("Âm Dương: " + (laSo.amDuongThuanLy ? "Thuận Lý" : "Nghịch Lý") + " | Cục: " + laSo.cuc.getTen() + "\n");

        System.out.println("=== BẢN ĐỒ 12 CUNG LÁ SỐ ===");
        for (DiaChi dc : DiaChi.values()) {
            CungDiaBan cung = laSo.diaBan.get(dc);

            List<String> tenCungChuc = new ArrayList<>();
            for (CungChuc cc : cung.cacCungChuc) tenCungChuc.add(cc.getTen());
            if (cung.laCungThan) tenCungChuc.add("Thân");

            List<String> tenSao = new ArrayList<>();
            for (Sao s : cung.cacSao) tenSao.add(s.getTen());

            System.out.printf("[ Cung %-4s ] - Chức: %-25s \n", dc.getTen(), String.join(", ", tenCungChuc));
            System.out.printf("              Sao: %s\n\n", String.join(", ", tenSao));
        }
    }
}