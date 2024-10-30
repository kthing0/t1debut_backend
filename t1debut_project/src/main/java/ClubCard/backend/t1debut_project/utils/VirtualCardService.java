package ClubCard.backend.t1debut_project.utils;

import ClubCard.backend.t1debut_project.dto.VirtualCardCustomizationDTO;
import ClubCard.backend.t1debut_project.dto.VirtualCardDTO;
import ClubCard.backend.t1debut_project.entities.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class VirtualCardService {

    private final UserRepository userRepository;

    public VirtualCardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public byte[] generateQRCode(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String data = "User: " + user.getEmail() + ", ID: " + user.getId() + ", Privilege: " + user.getPrivilege();
        return generateQRCodeImage(data, 300, 300);
    }

    private byte[] generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE); // bg color
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK); // QR color

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating QR Code", e);
        }
    }
    public VirtualCardDTO customizeVirtualCard(Long userId, VirtualCardCustomizationDTO customizationDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setTemplate(customizationDTO.getTemplate());
        userRepository.save(user);
        return new VirtualCardDTO(user);
    }

    public VirtualCardDTO getVirtualCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new VirtualCardDTO(user);
    }

}


