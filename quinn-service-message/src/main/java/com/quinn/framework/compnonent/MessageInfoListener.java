package com.quinn.framework.compnonent;

import com.quinn.framework.api.message.MessageInfoSupplier;
import com.quinn.framework.model.MessageInfoFactory;
import com.quinn.framework.model.MessageSenderFactory;
import com.quinn.framework.service.MessageHelpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 消息信息加载监听器
 *
 * @author Qunhua.Liao
 * @since 2020-06-02
 */
@Component
public class MessageInfoListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();

        // 消息信息提供者
        MessageInfoSupplier supplier = applicationContext.getBean(MessageInfoSupplier.class);
        MessageInfoFactory.setMessageInfoSupplier(supplier);

        // 消息数据库信息获取协助者
        MessageHelpService messageHelpService = applicationContext.getBean(MessageHelpService.class);
        MessageSenderFactory.setMessageServerService(messageHelpService);
    }

}