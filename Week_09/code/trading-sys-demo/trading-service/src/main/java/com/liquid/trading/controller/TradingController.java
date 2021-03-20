package com.liquid.trading.controller;

import com.liquid.trading.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易系统 Controller
 *
 * @author Liquid
 */
@RestController
@RequestMapping("/trading")
public class TradingController {
    private final TradingService tradingService;

    @Autowired
    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    /**
     * 直接模拟由交易系统完成用户A和用户B之间的交易
     *
     * @return
     */
    @GetMapping("/deal")
    public String trading() {
        tradingService.makeTrade();
        return "Success";
    }

}
