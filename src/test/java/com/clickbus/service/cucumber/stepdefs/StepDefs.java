package com.clickbus.service.cucumber.stepdefs;

import com.clickbus.service.ClickbusApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ClickbusApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
