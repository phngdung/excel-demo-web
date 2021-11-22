package com.demo.services.boy;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.repositories.BoyRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class BoyServiceImpl implements BoyService {
    @Autowired
    BoyRepository boyRepo;

    static String[] firstname = new String[]{
            "Đỗ", "Trần", "Bùi", "Chu", "Nguyễn", "Mai", "Lê", "Hoàng", "Lại"
    };
    static String[] lastname = new String[]{
            "Anh", "Bình", "Dương", "Gia", "Hiếu", "Hoàng", "Hưng", "Hữu", "Linh", "Long", "Minh",
            "Nghiên", "Phúc", "Thái", "Tiến", "Trọng", "Tuấn", "Văn", "Đạt"
    };
    static String[] exCity = new String[]{"Hà Nội", "HCM", "An Giang", "Vũng tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu",
            "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cần Thơ", "Cao Bằng",
            "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Bắc Ninh"};
    static String[] hobbit = new String[]{"Game", "Music"};
    static String[] hair = new String[]{"Black", "Grey", "White"};

    public List<Boy> makeData(int numOfData) {
        List<Boy> listBoy = new ArrayList<>();
        Boy boy;
        Random rand = new Random();
        String name, city;
        for (int i = 1; i <= numOfData; i++) {
            name = firstname[rand.nextInt(firstname.length)];
            name += " " + (lastname[rand.nextInt(lastname.length)]);
            name += " " + (lastname[rand.nextInt(lastname.length)]);
            city = exCity[rand.nextInt(lastname.length)];
            boy = new Boy(i, name, 18 + rand.nextInt(5), city, 160F + rand.nextInt(20), 50F + rand.nextInt(5), hobbit[rand.nextInt(2)], hair[rand.nextInt(3)], "None");
            listBoy.add(boy);
        }
        return listBoy;
    }
    public void save(Boy boy) {
        boyRepo.save(boy);
    }
    public List<Boy> listAll(){
        return (List<Boy>) boyRepo.findAll();
    }
    public Boy edit(long id, AddBoyRequest addBoyRequest){
        Boy boy= boyRepo.findById(id).get();
        boy.setName(addBoyRequest.getName());
        boy.setAge(addBoyRequest.getAge());
        boy.setCity(addBoyRequest.getCity());
        boy.setHeight(addBoyRequest.getHeight());
        boy.setWeight(addBoyRequest.getWeight());
        boy.setHobbit(addBoyRequest.getHobbit());
        boy.setHairColor(addBoyRequest.getHairColor());
        boy.setSkill(addBoyRequest.getSkill());
        boyRepo.save(boy);
        return boy;
    }

}