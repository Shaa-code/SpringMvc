package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String username){
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        log.info("memberRepository Class = {}", memberRepository.getClass());
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        log.info("logRepository Class = {}", logRepository.getClass());
        logRepository.save(logMessage);
        log.info("== logRepository 호출 종료 ==");
    }

    @Transactional
    public void joinV2(String username){
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e){
            log.info("log 저장에 실패했습니다. logMessage = {}", logMessage.getMessage());
            //로그 때문에 고객에게 문제를 줄 필요가 있나? 하는 생각으로 그냥 넘김 나중에 DB보고 직접 복구하자!
            log.info("정상 흐름 반환");
        }
        log.info("== logRepository 호출 종료 ==");
    }
}
