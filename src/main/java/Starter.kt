import org.apache.log4j.Logger
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

val log = Logger.getLogger("Starter")

fun main() {
    log.debug("Запускаем стартер бота")
    System.getProperties().put("proxySet", "true");
    System.getProperties().put("socksProxyHost", "127.0.0.1");
    System.getProperties().put("socksProxyPort", "9150");
    ApiContextInitializer.init()
    val telegramBotsApi = TelegramBotsApi()
    try {
        log.debug("Начинаем инициализацию бота")
        telegramBotsApi.registerBot(Bot())
        log.debug("Инициализация инициализацию бота")
    } catch (e: TelegramApiException) {
        log.error("Ошибка инициализации", e)
    }
}