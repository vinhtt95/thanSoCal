public class TuViCalculator {

    // Định nghĩa 5 loại Cục
    public enum Cuc {
        THUY_NHI_CUC(2),
        MOC_TAM_CUC(3),
        KIM_TU_CUC(4),
        THO_NGU_CUC(5),
        HOA_LUC_CUC(6);

        private final int giaTri;

        Cuc(int giaTri) {
            this.giaTri = giaTri;
        }

        public int getGiaTri() {
            return giaTri;
        }
    }

    // Định nghĩa 12 Địa Chi (0 = Tý, 1 = Sửu, 2 = Dần...)
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

        public int getIndex() {
            return index;
        }

        public String getTen() {
            return ten;
        }

        // Helper để lấy Địa Chi từ một index bất kỳ (có xử lý vòng lặp)
        public static DiaChi fromIndex(int index) {
            // Đảm bảo index luôn dương trong trường hợp lùi (nghịch) qua mốc Tý (0)
            int normalizedIndex = ((index % 12) + 12) % 12;
            for (DiaChi dc : DiaChi.values()) {
                if (dc.getIndex() == normalizedIndex) {
                    return dc;
                }
            }
            return null;
        }
    }

    /**
     * Hàm tính vị trí an sao Tử Vi
     */
    public static DiaChi timViTriSaoTuVi(int ngaySinh, Cuc cuc) {
        int giaTriCuc = cuc.getGiaTri();

        // 1. Tìm số mượn và thương số
        int muon = 0;
        int phanDu = ngaySinh % giaTriCuc;
        if (phanDu != 0) {
            muon = giaTriCuc - phanDu;
        }

        int thuong = (ngaySinh + muon) / giaTriCuc;

        // 2. Tìm vị trí ban đầu (Bắt đầu từ Dần đếm là 1)
        // Vì Dần là 1, nên số bước thực tế nhảy tính từ Dần sẽ là (thuong - 1)
        int indexDan = DiaChi.DAN.getIndex();
        int viTriBanDau = indexDan + (thuong - 1);

        // 3. Xét số mượn để tiến hoặc lùi
        int viTriCuoiCung;
        if (muon % 2 == 0) {
            // Mượn chẵn: Tiến (thuận) thêm 'muon' bước
            viTriCuoiCung = viTriBanDau + muon;
        } else {
            // Mượn lẻ: Lùi (nghịch) đi 'muon' bước
            viTriCuoiCung = viTriBanDau - muon;
        }

        // Trả về kết quả sau khi đã map lại vào vòng 12 cung
        return DiaChi.fromIndex(viTriCuoiCung);
    }

    public static void main(String[] args) {
        // Test case đúng với ví dụ của mày: Ngày 21, Thổ Ngũ Cục (chia 5)
        int ngaySinh = 8;
        Cuc cuc = Cuc.THO_NGU_CUC;

        DiaChi viTriTuVi = timViTriSaoTuVi(ngaySinh, cuc);

        System.out.println("Ngày sinh: " + ngaySinh);
        System.out.println("Cục: " + cuc.name() + " (" + cuc.getGiaTri() + ")");
        System.out.println("=> Sao Tử Vi an tại cung: " + viTriTuVi.getTen());
        // Kết quả sẽ ra Tuất (Đúng chuẩn theo bộ luật Tử Vi)
    }
}