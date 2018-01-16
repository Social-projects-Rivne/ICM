package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.Constants;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

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
public class UserProfileService{

    private UserDao userDao;
    private BCryptPasswordEncoder encoder;
    private UserMapper mapper;

    private static final String PATH = "src/main/resources/users/logo/";
    private static final Logger LOGGER = Logger.getLogger(UserProfileService.class.getName());

    @Autowired
    public UserProfileService(UserDao userDao, BCryptPasswordEncoder encoder, UserMapper mapper) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    public UserDto updatePassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        checkArgument(encoder.matches(oldPassword, user.getPassword()), "The user " + email
                + " entered an incorrect password");
        checkArgument(newPassword.length() >= 2, Constants.SHORT_PASSWORD);
        user.setPassword(encoder.encode(newPassword));
        userDao.save(user);
        LOGGER.debug("User " + user.getUsername() + " has changed his password");
        return mapper.toDto(user);
    }

    public UserDto updateContactsInfo(String email, String fistName, String lastName, String phone) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        if (fistName != null && !fistName.isEmpty())
            user.setFirstName(fistName);
        if (lastName != null && !lastName.isEmpty())
            user.setLastName(lastName);
        if (phone != null && !phone.isEmpty())
            user.setPhone(phone);
        try {
            userDao.save(user);
            LOGGER.debug("User " + user.getUsername() + " has changed his contacts form");
        } catch (Exception e){
            throw new IllegalArgumentException("User " + user.getUsername() + " has wrote incorrect data");
        }
        return mapper.toDto(user);
    }

    public void updatePortfolioPhoto(MultipartFile photo, String email){
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "User with the following email \'" + email + "\' doesn't not exist");
        checkArgument(isImage(photo), "Is not a image");

        long id = user.getId();
        String avatarUrl = createImages(id, photo);
        user.setAvatarUrl(avatarUrl);
        userDao.save(user);
    }

    public byte[] getOriginalAvatar(long id) throws IOException {
        return getAvatar(id, "original.png");
    }

    public byte[] getMediumAvatar(long id) throws IOException {
        return getAvatar(id, "medium.png");
    }

    public byte[] getSmallAvatar(long id) throws IOException {
        return getAvatar(id, "small.png");
    }

    private byte[] getAvatar(long id, String name) throws IOException {
        User user = userDao.findOne(id);
        checkArgument(user != null, "User with the following id \'" + id + "\' doesn't not exist");
        return user.getAvatarUrl() == null ? defaultEmptyAvatar() : user.getAvatarUrl().contains("http://url.com")
                ? defaultEmptyAvatar() : Files.readAllBytes(Paths.get(user.getAvatarUrl(), name));
    }

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

    private byte[] defaultEmptyAvatar() throws IOException {
        URL url = new URL("http://www.teequilla.com/images/tq/empty-avatar.png");
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n;
            byte [] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        return output.toByteArray();
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

            return getDirPath(image.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDirPath(String path){
        String[] strings = path.split("/");
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i <= strings.length - 2; i++){
            temp.append(strings[i]).append("/");
        }
        return temp.toString();
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