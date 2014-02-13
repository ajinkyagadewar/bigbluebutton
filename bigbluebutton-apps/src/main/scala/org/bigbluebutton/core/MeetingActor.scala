package org.bigbluebutton.core

import scala.actors.Actor
import scala.actors.Actor._
import org.bigbluebutton.core.apps.poll.PollApp
import org.bigbluebutton.core.apps.poll.Poll
import org.bigbluebutton.core.apps.poll.PollApp
import org.bigbluebutton.core.apps.users.UsersApp
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.apps.presentation.PresentationApp
import org.bigbluebutton.core.apps.layout.LayoutApp
import org.bigbluebutton.core.apps.chat.ChatApp
import org.bigbluebutton.core.apps.whiteboard.WhiteboardApp
import net.lag.logging.Logger

case object StopMeetingActor

case class LockSettings(allowModeratorLocking: Boolean, disableCam: Boolean, 
                        disableMic: Boolean, disablePrivateChat: Boolean, 
                        disablePublicChat: Boolean)
                         
class MeetingActor(val meetingID: String, val recorded: Boolean, 
                   val voiceBridge: String, val outGW: MessageOutGateway) 
                   extends Actor with UsersApp with PresentationApp
                   with PollApp with LayoutApp with ChatApp
                   with WhiteboardApp {  
  private val log = Logger.get
  
  var lockSettings = new LockSettings(true, true, true, true, true)
  var recordingStatus = false;

  def act() = {
	loop {
	  react {
	    case msg: VoiceUserJoined =>
	                         handleVoiceUserJoined(msg)
	    case msg: VoiceUserLeft =>
	                         handleVoiceUserLeft(msg)
	    case msg: VoiceUserMuted =>
	                         handleVoiceUserMuted(msg)
	    case msg: VoiceUserTalking =>
	                         handleVoiceUserTalking(msg)
    	case msg: UserJoining => 
      	                     handleUserJoin(msg)
	    case msg: UserLeaving => 
	                         handleUserLeft(msg)
	    case msg: AssignPresenter => 
	                         handleAssignPresenter(msg)
	    case msg: GetUsers => 
	                         handleGetUsers(msg)
	    case msg: ChangeUserStatus => 
	                         handleChangeUserStatus(msg)
	    case msg: UserRaiseHand =>
	                         handleUserRaiseHand(msg)
	    case msg: UserLowerHand =>
	                         handleUserLowerHand(msg)
	    case msg: UserShareWebcam =>
	                         handleUserShareWebcam(msg)
	    case msg: UserUnshareWebcam =>
	                         handleUserunshareWebcam(msg)
	    case msg: MuteMeetingRequest => 
	                         handleMuteMeetingRequest(msg)
	    case msg: IsMeetingMutedRequest => 
	                         handleIsMeetingMutedRequest(msg)
	    case msg: MuteUserRequest => 
	                         handleMuteUserRequest(msg)
	    case msg: LockUserRequest => 
	                         handleLockUserRequest(msg)
	    case msg: EjectUserRequest => 
	                         handleEjectUserRequest(msg)
	    case msg: SetLockSettings => 
	                         handleSetLockSettings(msg)
	    case msg: InitLockSettings => 
	                         handleInitLockSettings(msg)
	    case msg: LockUser => 
	                         handleLockUser(msg)
	    case msg: LockAllUsers => 
	                         handleLockAllUsers(msg)
	    case msg: GetLockSettings => 
	                         handleGetLockSettings(msg)
	    case msg: IsMeetingLocked => 
	                         handleIsMeetingLocked(msg)
	    case msg: GetChatHistoryRequest => 
	                         handleGetChatHistoryRequest(msg) 
	    case msg: SendPublicMessageRequest => 
	                         handleSendPublicMessageRequest(msg)
	    case msg: SendPrivateMessageRequest => 
	                         handleSendPrivateMessageRequest(msg)
	    case msg: GetCurrentLayoutRequest => 
	                         handleGetCurrentLayoutRequest(msg)
	    case msg: SetLayoutRequest => 
	                         handleSetLayoutRequest(msg)
	    case msg: LockLayoutRequest => 
	                         handleLockLayoutRequest(msg)
	    case msg: UnlockLayoutRequest => 
	                         handleUnlockLayoutRequest(msg)
	    case msg: InitializeMeeting => 
	                         handleInitializeMeeting(msg)
    	case msg: ClearPresentation => 
    	                     handleClearPresentation(msg)
    	case msg: PresentationConversionUpdate =>
    	                     handlePresentationConversionUpdate(msg)
    	case msg: PresentationPageCountError =>
    	                     handlePresentationPageCountError(msg)
    	case msg: PresentationSlideGenerated =>
    	                     handlePresentationSlideGenerated(msg)
    	case msg: PresentationConversionCompleted =>
    	                     handlePresentationConversionCompleted(msg)
    	case msg: RemovePresentation => 
    	                     handleRemovePresentation(msg)
    	case msg: GetPresentationInfo => 
    	                     handleGetPresentationInfo(msg)
    	case msg: SendCursorUpdate => 
    	                     handleSendCursorUpdate(msg)
    	case msg: ResizeAndMoveSlide => 
    	                     handleResizeAndMoveSlide(msg)
    	case msg: GotoSlide => 
    	                     handleGotoSlide(msg)
    	case msg: SharePresentation => 
    	                     handleSharePresentation(msg)
    	case msg: GetSlideInfo => 
    	                     handleGetSlideInfo(msg)
    	case msg: PreuploadedPresentations => 
    	                     handlePreuploadedPresentations(msg)
        case msg: PreCreatedPoll => 
                             handlePreCreatedPoll(msg)
        case msg: CreatePoll => 
                             handleCreatePoll(msg)
        case msg: UpdatePoll => 
                             handleUpdatePoll(msg)
        case msg: DestroyPoll => 
                             handleDestroyPoll(msg)
        case msg: RemovePoll => 
                             handleRemovePoll(msg)
        case msg: SharePoll => 
                             handleSharePoll(msg)
        case msg: StopPoll => 
                             handleStopPoll(msg)
        case msg: StartPoll => 
                             handleStartPoll(msg)
        case msg: ClearPoll => 
                             handleClearPoll(msg)
        case msg: GetPolls => 
                             handleGetPolls(msg)
        case msg: RespondToPoll => 
                             handleRespondToPoll(msg)
        case msg: HidePollResult => 
                             handleHidePollResult(msg)
        case msg: ShowPollResult => 
                             handleShowPollResult(msg)
	    case msg: SendWhiteboardAnnotationRequest => 
	                         handleSendWhiteboardAnnotationRequest(msg)
	    case msg: SetWhiteboardActivePageRequest => 
	                         handleSetWhiteboardActivePageRequest(msg)
	    case msg: SendWhiteboardAnnotationHistoryRequest => 
	                         handleSendWhiteboardAnnotationHistoryRequest(msg)
	    case msg: ClearWhiteboardRequest => 
	                         handleClearWhiteboardRequest(msg)
	    case msg: UndoWhiteboardRequest => 
	                         handleUndoWhiteboardRequest(msg)
	    case msg: SetActivePresentationRequest => 
	                         handleSetActivePresentationRequest(msg)
	    case msg: EnableWhiteboardRequest => 
	                         handleEnableWhiteboardRequest(msg)
	    case msg: IsWhiteboardEnabledRequest => 
	                         handleIsWhiteboardEnabledRequest(msg)
	    case msg: SetRecordingStatus =>
	                handleSetRecordingStatus(msg)
	    case msg: GetRecordingStatus =>
	                handleGetRecordingStatus(msg)
	    case msg: VoiceRecording =>
	                handleVoiceRecording(msg)
	    case StopMeetingActor => exit
	    case _ => // do nothing
	  }
	}
  }	
  
  private def handleVoiceRecording(msg: VoiceRecording) {
     if (msg.recording) {
       outGW.send(new VoiceRecordingStarted(meetingID, 
                        recorded, msg.recordingFile, 
                        msg.timestamp, voiceBridge))
     } else {
       outGW.send(new VoiceRecordingStopped(meetingID, recorded, 
                        msg.recordingFile, msg.timestamp, voiceBridge))
     }
  }
  
  private def handleSetRecordingStatus(msg: SetRecordingStatus) {
     recordingStatus = msg.recording
     outGW.send(new RecordingStatusChanged(meetingID, recorded, msg.userId, msg.recording))
  }   

  private def handleGetRecordingStatus(msg: GetRecordingStatus) {
     outGW.send(new GetRecordingStatusReply(meetingID, recorded, msg.userId, recordingStatus.booleanValue()))
  } 
}