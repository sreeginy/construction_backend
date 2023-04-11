package com.system.repository;

import com.system.model.CommonResponse;
import com.system.model.Material;
import com.system.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public interface MaterialRepository extends JpaRepository<Material,Integer> {
    Material findByName(String name);

}
