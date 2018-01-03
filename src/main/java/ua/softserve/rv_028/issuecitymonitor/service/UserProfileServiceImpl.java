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

    private static final Logger LOGGER = Logger.getLogger(UserProfileServiceImpl.class.getName());
    private final Path rootLocation = Paths.get("photos");

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

    private final String PATH = "src/main/resources/users/logo/";
    private final String ORIGINAL = "original.png";
    private final String MEDIUM_SIZE = "medium.png";

    @Override
    public void updatePortfolioPhoto(MultipartFile photo, String email){
        long id = userDao.findUserByUsername(email).getId();

        checkArgument(isImage(photo), "Is not a image");

        System.out.println("createImages:\t" + createImages(id, photo));

        try {
            BufferedImage bufferedImage = createResizedCopy(ImageIO.read(photo.getInputStream()), 250, 250, true);

            System.out.println(bufferedImage.getHeight());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", stream);
            Path userPhotoPath = Files.createDirectories(Paths.get(PATH, String.valueOf(id)));
            File file = new File(userPhotoPath.toString(), MEDIUM_SIZE);

            FileOutputStream out = new FileOutputStream(file);
            out.write(stream.toByteArray());
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

/*        try {

            File temp = new File(photo.getOriginalFilename());
            String s = Files.probeContentType(temp.toPath());
            System.out.println(s);

            Path userPhotoPath = Files.createDirectories(Paths.get(PATH, String.valueOf(id)));
            File file = new File(userPhotoPath.toString(), photo.getOriginalFilename());

            FileOutputStream out = new FileOutputStream(file);
            out.write(photo.getBytes());
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("FAIL!");
        }*/

        URL resource = getClass().getClassLoader().getResource("users/logo/1151/original.png");
        if (resource != null)
            System.out.println(resource.getPath());
        else
            System.out.println("null");
    }

    BufferedImage createResizedCopy(Image originalImage,
                                    int scaledWidth, int scaledHeight,
                                    boolean preserveAlpha){
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    private String createImages(long userId, MultipartFile photo) {
        try {
            Path userPhotoPath = Files.createDirectories(Paths.get(PATH, String.valueOf(userId)));
            File file = new File(userPhotoPath.toString(), ORIGINAL);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(ImageIO.read(photo.getInputStream()),"png", byteArrayOutputStream);

            FileOutputStream out = new FileOutputStream(file);
            out.write(byteArrayOutputStream.toByteArray());
            out.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isImage(MultipartFile file){
        try {
            InputStream input = file.getInputStream();
            System.out.println("type:\t" + ImageIO.read(input).getType());
            return true;
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

    @Override
    public Map getUserInfo(String email) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getUsername());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("authorities", user.getAuthorities());
        map.put("phone", user.getPhone());
        return map;
    }
}