public class CucCalculator {

    // 1. Định nghĩa 10 Thiên Can
    public enum ThienCan {
        GIAP(0, "Giáp"), AT(1, "Ất"), BINH(2, "Bính"), DINH(3, "Đinh"), MAU(4, "Mậu"),
        KY(5, "Kỷ"), CANH(6, "Canh"), TAN(7, "Tân"), NHAM(8, "Nhâm"), QUY(9, "Quý");

        private final int index;
        private final String ten;

        ThienCan(int index, String ten) {
            this.index = index;
            this.ten = ten;
        }

        public int getIndex() { return index; }
        public String getTen() { return ten; }
    }

    // 2. Định nghĩa 12 Địa Chi (Dùng chung chuẩn với các hàm trước)
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
    }

    // 3. Định nghĩa Cục (Kèm giá trị map trong Sheet và hệ số chia)
    public enum Cuc {
        THUY_NHI_CUC(0, 2, "Thủy Nhị Cục"),
        MOC_TAM_CUC(1, 3, "Mộc Tam Cục"),
        KIM_TU_CUC(2, 4, "Kim Tứ Cục"),
        THO_NGU_CUC(3, 5, "Thổ Ngũ Cục"),
        HOA_LUC_CUC(4, 6, "Hỏa Lục Cục");

        private final int mapValue; // ID map theo CSV (0 -> 4)
        private final int heSoChia; // Dùng cho bài toán tìm vị trí sao Tử Vi (2 -> 6)
        private final String ten;

        Cuc(int mapValue, int heSoChia, String ten) {
            this.mapValue = mapValue;
            this.heSoChia = heSoChia;
            this.ten = ten;
        }

        public int getHeSoChia() { return heSoChia; }
        public String getTen() { return ten; }

        public static Cuc fromMapValue(int val) {
            for (Cuc c : values()) {
                if (c.mapValue == val) return c;
            }
            throw new IllegalArgumentException("Không tìm thấy Cục với mã: " + val);
        }
    }

    // 4. Ma trận tra cứu Cục O(1)
    // Hàng (0-11) đại diện cho Cung an Mệnh (Tý -> Hợi)
    // Cột (0-9) đại diện cho Thiên can năm sinh (Giáp -> Quý)
    private static final int[][] MATRIX_CUC = {
            {0, 4, 3, 1, 2, 0, 4, 3, 1, 2}, // Hàng 0 - Tý
            {0, 4, 3, 1, 2, 0, 4, 3, 1, 2}, // Hàng 1 - Sửu
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, // Hàng 2 - Dần
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, // Hàng 3 - Mão
            {1, 2, 0, 4, 3, 1, 2, 0, 4, 3}, // Hàng 4 - Thìn
            {1, 2, 0, 4, 3, 1, 2, 0, 4, 3}, // Hàng 5 - Tỵ
            {3, 1, 2, 0, 4, 3, 1, 2, 0, 4}, // Hàng 6 - Ngọ
            {3, 1, 2, 0, 4, 3, 1, 2, 0, 4}, // Hàng 7 - Mùi
            {2, 0, 4, 3, 1, 2, 0, 4, 3, 1}, // Hàng 8 - Thân
            {2, 0, 4, 3, 1, 2, 0, 4, 3, 1}, // Hàng 9 - Dậu
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}, // Hàng 10 - Tuất
            {4, 3, 1, 2, 0, 4, 3, 1, 2, 0}  // Hàng 11 - Hợi
    };

    /**
     * Hàm tìm Cục chính
     */
    public static Cuc timCuc(ThienCan canNamSinh, DiaChi cungMenh) {
        int canIndex = canNamSinh.getIndex();
        int chiIndex = cungMenh.getIndex();

        // Tra cứu trực tiếp tọa độ (Hàng, Cột) trên ma trận
        int cucMapValue = MATRIX_CUC[chiIndex][canIndex];

        return Cuc.fromMapValue(cucMapValue);
    }

    public static void main(String[] args) {
        // Test case: Năm sinh Ất (1), Cung Mệnh tại Tý (0)
        ThienCan canNamSinh = ThienCan.AT;
        DiaChi cungMenh = DiaChi.TY;

        Cuc ketQua = timCuc(canNamSinh, cungMenh);

        System.out.println("Thiên can năm sinh: " + canNamSinh.getTen());
        System.out.println("Cung an Mệnh: " + cungMenh.getTen());
        System.out.println("=> Kết quả Cục: " + ketQua.getTen() + " (Hệ số chia: " + ketQua.getHeSoChia() + ")");
        // Theo mảng [0][1], kết quả trả ra phải là 4 (Hỏa Lục Cục)
    }
}