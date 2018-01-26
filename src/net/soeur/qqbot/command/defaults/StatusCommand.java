package net.soeur.qqbot.command.defaults;

import net.soeur.qqbot.command.CommandReceiver;
import net.soeur.qqbot.command.CommandSender;

public class StatusCommand implements CommandReceiver {

    public void exec(CommandSender sender, String[] args) {
        StringBuffer info = new StringBuffer();
        Runtime runtime = Runtime.getRuntime();
        long ts = System.nanoTime();
        info.append("运行环境：")
                .append(System.getProperty("utils.name"))
                .append("\n")
                .append("虚拟机可用最大内存：")
                .append(runtime.maxMemory() / 1024.0 / 1024.0)
                .append("MB\n")
                .append("虚拟机已用内存：")
                .append(runtime.totalMemory() / 1024.0 / 1024.0)
                .append("MB\n")
                .append("虚拟机空闲内存:")
                .append(runtime.freeMemory() / 1024.0 / 1024.0)
                .append("MB\n")
                .append("程序已用内存：")
                .append((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024)
                .append("MB\n")
                .append("可用cpu数量：")
                .append(runtime.availableProcessors())
                .append("个\n")
                .append("查询耗时：")
                .append((System.nanoTime() - ts) / 1000000.00)
                .append("ms");
        sender.sendMessage(info.toString());
    }

}
