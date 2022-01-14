package com.demo.services.boy;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.entities.BoyPage;
import com.demo.entities.BoySearchCriteria;
import com.demo.repositories.BoyCriteriaRepository;
import com.demo.repositories.BoyRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class BoyService {
    @Autowired
    BoyRepository boyRepo;

    private final BoyRepository boyRepository;
    private final BoyCriteriaRepository boyCriteriaRepository;

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

    public List<Boy> listAll() {
        return (List<Boy>) boyRepo.findAll();
    }

    public Boy edit(long id, AddBoyRequest addBoyRequest) {
        Boy boy = boyRepo.findById(id).get();
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

    public Boy add( AddBoyRequest addBoyRequest) {
        Boy newBoy= new Boy(addBoyRequest);
        boyRepo.save(newBoy);
        return newBoy;
    }

    public void delete(long id) {
        boyRepo.deleteById(id);
    }
    public Boy get(long id) {
        return boyRepo.findById(id).get();
    }

    public List<Boy> getByOptions(String sortBy, String filterBy) {
        EntityManager em = boyRepo.entityManager;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Boy> cq = cb.createQuery(Boy.class);
        Root<Boy> pet = cq.from(Boy.class);
        cq.select(pet);
        TypedQuery<Boy> q = em.createQuery(cq);
        List<Boy> allPets = q.getResultList();
        return (List<Boy>) (null);
    }


    public BoyService(BoyRepository boyRepository,
                      BoyCriteriaRepository boyCriteriaRepository) {
        this.boyRepository = boyRepository;
        this.boyCriteriaRepository = boyCriteriaRepository;
    }

    public Page<Boy> getBoysByOptions(BoyPage boyPage,
                                      BoySearchCriteria boySearchCriteria) {
        return boyCriteriaRepository.findAllWithFilters(boyPage, boySearchCriteria);
    }
}