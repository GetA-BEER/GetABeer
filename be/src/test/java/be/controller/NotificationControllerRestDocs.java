package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.NotificationControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.gson.Gson;

import be.domain.notice.service.NotificationService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class NotificationControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private NotificationService notificationService;

	@Test
	void subscribeTest() throws Exception {

		String lastEventId = "";

		given(notificationService.subscribe(anyString())).willReturn(new SseEmitter());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/subscribe")
					.header("Last-Event-ID", lastEventId)
					.accept(MediaType.TEXT_EVENT_STREAM)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Subscribe"
			));
	}

	@Test
	void getMyNotificationsTest() throws Exception {

		given(notificationService.findAll()).willReturn(NOTIFICATION_TOTAL_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/notifications")
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Notifications",
				getDocumentResponse(),
				responseFields(
					List.of(
						fieldWithPath("notifications[].").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath("notifications[].id").type(JsonFieldType.NUMBER).description("알림 번호"),
						fieldWithPath("notifications[].title").type(JsonFieldType.STRING).description("알림 내용"),
						fieldWithPath("notifications[].commentUserImage").type(JsonFieldType.STRING)
							.description("프로필 사진"),
						fieldWithPath("notifications[].content").type(JsonFieldType.STRING).description("대상 컨텐츠"),
						fieldWithPath("notifications[].notifyType").type(JsonFieldType.STRING).description("알림 타입"),
						fieldWithPath("notifications[].idForNotifyType").type(JsonFieldType.NUMBER)
							.description("알림 타입 번호"),
						fieldWithPath("notifications[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
						fieldWithPath("notifications[].isRead").type(JsonFieldType.BOOLEAN).description("확인 여부"),
						fieldWithPath(".unreadCount").type(JsonFieldType.NUMBER).description("확인 안 한 알림 개수")
					)
				)));
	}

	@Test
	void readNotificationTest() throws Exception {

		Long notificationId = 1L;

		doNothing().when(notificationService).readNotification(anyLong());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/notifications/{notification-id}", notificationId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Read_Notifications",
				pathParameters(
					parameterWithName("notification-id").description("알림 번호")
				)
			));
	}

	@Test
	void deleteNotificationTest() throws Exception {

		Long notificationId = 1L;

		doNothing().when(notificationService).deleteNotification(anyLong());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/notifications/{notification-id}", notificationId)
			)
			.andExpect(status().isNotFound())
			.andDo(
				document(
					"Delete_Notification",
					pathParameters(
						parameterWithName("notification-id").description("알림 번호")
					)
				)
			);
	}

	// @Test
	// void deleteAllMyNotificationsTest() throws Exception {
	//
	// 	doNothing().when(notificationService).deleteAllMyNotifications();
	//
	// 	mockMvc.perform(
	// 			RestDocumentationRequestBuilders.delete("/notifications/delete")
	// 		)
	// 		.andExpect(status().isNoContent())
	// 		.andDo(
	// 			document(
	// 				"Delete_All_Notification"
	// 			)
	// 		);
	// }
}
