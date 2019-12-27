import org.apache.log4j.Logger
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class Bot : TelegramLongPollingBot() {
    val log = Logger.getLogger(Bot::class.java)

    /**
     * Получить имя бота AshamazVebinarBot
     * */
    override fun getBotUsername(): String {
        return "AshamazVebinarBot"
    }

    /**
     * Получить токен
     * */
    override fun getBotToken(): String {
        return "1008641657:AAE1fmO_o2BXk8FOkRiDPyaOPAON_i6e_24"
    }

    /**
     * Метод для приема сообщений. Используется для получения обновлений
     * */
    override fun onUpdateReceived(update: Update?) {
        val model = Model()
        val message = update?.message
        log.info("Получили сообщение $message")
        when (message?.text) {
            "/help" -> (sendMsg(message, "Чем могу помочь?"))
            "/setting" -> (sendMsg(message, "Что будем настраивать?"))
            "/settings" -> (sendMsg(message, "Что будем настраивать 2?"))
            else -> {
                try{
                    (sendMsg(message, Weather.getWeather(message?.text?:"London", model)))
                } catch (e: Exception){
                    sendMsg(message, "Не удалось определить город")
                }
            }
        }
    }

    fun setButtons(sendMessage: SendMessage) {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        sendMessage.replyMarkup = replyKeyboardMarkup
        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        replyKeyboardMarkup.oneTimeKeyboard = false

        val keyboardRow = KeyboardRow()
        keyboardRow.add(KeyboardButton("/help"))
        keyboardRow.add(KeyboardButton("/setting"))

        val keyboardRowList = listOf(keyboardRow)

        replyKeyboardMarkup.setKeyboard(keyboardRowList)


    }

    private fun sendMsg(message: Message?, str: String) {
        val sm = SendMessage()
        sm.enableMarkdown(true)
        sm.setChatId(message?.chatId.toString())
        sm.setReplyToMessageId(message?.messageId)
        sm.setText(str)
        log.info("Отправляем сообщение $str в чат с id = ${message?.chatId.toString()}")
        try {
            setButtons(sm)
            execute(sm)
        } catch (e: TelegramApiException) {
            log.error("Ошибка отправки сообщения", e)
        }
    }
}