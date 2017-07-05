/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring;

import com.sguild.flooring.controller.FlooringController;
import com.sguild.flooring.dao.FlooringConfigDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class App {

    public static void main(String[] args) {
        //INSTNATIATE NEW APP CTX
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        //INSTANTIATE CONFIG DAO AND IO
        FlooringConfigDao configOptions = ctx.getBean("configDao", FlooringConfigDao.class);
        //INSTANTIATE SERVICE LAYER AND USE IF ELSE TO SETTER INJECT THE ORDERDAO
        
           if(!configOptions.getIsTraining()){
               FlooringController controller
                        = ctx.getBean("controller1", FlooringController.class);
               controller.run();
           }else if(configOptions.getIsTraining()){
              FlooringController controller
                        = ctx.getBean("controller2", FlooringController.class);
               controller.run();
           }
    }
}
