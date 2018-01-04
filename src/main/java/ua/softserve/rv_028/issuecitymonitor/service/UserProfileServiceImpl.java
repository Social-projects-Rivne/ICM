package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.Constants;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private UserDao userDao;
    private BCryptPasswordEncoder encoder;

    private static final String PATH = "src/main/resources/users/logo/";
    private static final Logger LOGGER = Logger.getLogger(UserProfileServiceImpl.class.getName());

    @Autowired
    public UserProfileServiceImpl(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        checkArgument(encoder.matches(oldPassword, user.getPassword()), "The user " + email
                + " entered an incorrect password");
        checkArgument(newPassword.length() >= 2, Constants.SHORT_PASSWORD);
        user.setPassword(encoder.encode(newPassword));
        userDao.save(user);
        LOGGER.debug("User " + user.getUsername() + " has changed his password");
    }

    @Override
    public void updateContactsInfo(String email, String fistName, String lastName, String phone) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        if (fistName != null && !fistName.isEmpty())
            user.setFirstName(fistName);
        if (lastName != null && !lastName.isEmpty())
            user.setLastName(lastName);
        if (phone != null && !phone.isEmpty())
            user.setPhone(phone);
        userDao.save(user);
        LOGGER.debug("User " + user.getUsername() + " has changed his contacts form");
    }

    @Override
    public void updatePortfolioPhoto(MultipartFile photo, String email){
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "User with the following email \'" + email + "\' doesn't not exist");
        checkArgument(isImage(photo), "Is not a image");

        long id = user.getId();
        String avatarUrl = createImages(id, photo);
        user.setAvatarUrl(avatarUrl);
    }

    @Override
    public Map getUserInfo(String email) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("email", user.getUsername());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("authorities", user.getAuthorities());
        map.put("phone", user.getPhone());
        return map;
    }

    private String createImages(long userId, MultipartFile photo) {
        try {
            Path userPhotoPath = Files.createDirectories(Paths.get(PATH, String.valueOf(userId)));

            File image = new File(userPhotoPath.toString(), "original.png");
            writeFile(image, photo);

            image = new File(userPhotoPath.toString(), "medium.png");
            writeFile(image, createResizedCopy(ImageIO.read(photo.getInputStream()), 200, 200));

            image = new File(userPhotoPath.toString(), "small.png");
            writeFile(image, createResizedCopy(ImageIO.read(photo.getInputStream()), 35, 35));

            return image.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeFile(File file, BufferedImage stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        ImageIO.write(stream,"png", byteArrayOutputStream1);

        FileOutputStream out = new FileOutputStream(file);
        out.write(byteArrayOutputStream1.toByteArray());
        out.close();
    }

    private void writeFile(File file, MultipartFile photo) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(photo.getInputStream()),"png", byteArrayOutputStream);

        FileOutputStream out = new FileOutputStream(file);
        out.write(byteArrayOutputStream.toByteArray());
        out.close();
    }

    private BufferedImage createResizedCopy(Image originalImage,
                                            int scaledWidth, int scaledHeight){
        BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private boolean isImage(MultipartFile file){
        try {
            InputStream input = file.getInputStream();
            ImageIO.read(input).getType();
            return true;
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }
}