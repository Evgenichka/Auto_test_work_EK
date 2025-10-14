package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MyTestWatcher implements TestWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTestWatcher.class);

    @Override
    public void testDisabled(ExtensionContext context, String reason) {
        LOGGER.info("Test disabled: {}, Reason: {}", context.getDisplayName(), reason);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        LOGGER.info("Test successful: {}", context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        LOGGER.warn("Test aborted: {}", context.getDisplayName(), cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOGGER.error("Test failed: {}", context.getDisplayName(), cause);

        try {
            // Получаем экземпляр ApplicationManager из атрибутов контекста
            ApplicationManager app = context.getStore(ExtensionContext.Namespace.GLOBAL)
                    .get("app", ApplicationManager.class);

            // Берём скриншот экрана и сохраняем его в формате Attachment
            byte[] screenshotBytes = app.takeScreenshot();
            saveScreenshot(screenshotBytes);
        } catch (Throwable t) {
            LOGGER.error("Error saving screenshot.", t);
        }
    }

    // Аттачмент для сохранения скриншота
    public void saveScreenshot(byte[] screenshotBytes) {
        Path tempPath = null;
        try {
            tempPath = Files.createTempFile("screenshot_", ".png");
            Files.write(tempPath, screenshotBytes, StandardCopyOption.CREATE_NEW_ONLY);
            LOGGER.info("Screenshot saved to {}", tempPath.toAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Could not write screenshot file.", e);
        } finally {
            if (tempPath != null && Files.exists(tempPath)) {
                try {
                    Files.deleteIfExists(tempPath);
                } catch (Exception ignore) {
                    // Сообщаем, что удаление временного файла не удалось
                    LOGGER.warn("Temporary screenshot file could not be deleted.");
                }
            }
        }
    }
}