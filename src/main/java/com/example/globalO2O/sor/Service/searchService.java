package com.example.globalO2O.sor.Service;

import com.example.globalO2O.sor.DAO.Announ;
import com.example.globalO2O.sor.DAO.AnnounceRepo;
import com.example.globalO2O.sor.DTO.ApiDto;
import com.example.globalO2O.sor.DTO.ApiResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

@Service
public class searchService {

    static AnnounceRepo announceRepo;

    @Autowired
    public searchService(AnnounceRepo announceRepo){
        this.announceRepo = announceRepo;
    }

    public boolean isRegist(int annNo){
        int size = announceRepo.findAnnNo(annNo).size();
        System.out.println("size = " + size);
        if(size == 0) return false;
        return true;
    }

    public ApiResultDto findAnnNo(ApiDto dto){
        Announ ann = announceRepo.findByCondition(dto.getAnnNo(), dto.getAnnRuteNm(), dto.getShipngPrtNm(), dto.getLandngPrtNm(), dto.getCodPrtNm(), dto.getTsYn(), dto.getContnOwnSeNm(), dto.getContnCndNm(), dto.getContnStdStndrdNm(), dto.getFrghtNm(), dto.getTnspotSeNm()).remove(0);
        ApiResultDto result = ApiResultDto.builder()
                .cychgOf(ann.getCychgOf())
                .cychgOfCurCd(ann.getCychgOfCurCd())
                .cychgBaf(ann.getCychgBaf())
                .cychgBafCurCd(ann.getCychgBafCurCd())
                .cychgCaf(ann.getCychgCaf())
                .cychgCafCurCd(ann.getCychgCafCurCd())
                .cychgLss(ann.getCychgLss())
                .cychgLssCurCd(ann.getCychgLssCurCd())
                .cychgOthc(ann.getCychgOthc())
                .cychgOthcCurCd(ann.getCychgOthcCurCd())
                .cychgDthc(ann.getCychgDthc())
                .cychgDthcCurCd(ann.getCychgDthcCurCd())
                .cychgDf(ann.getCychgDf())
                .cychgDfCurCd(ann.getCychgDfCurCd())
                .cychgDo(ann.getCychgDo())
                .cychgDoCurCd(ann.getCychgDoCurCd())
                .cychgWaf(ann.getCychgWaf())
                .cychgWafCurCd(ann.getCychgWafCurCd())
                .cychgSc(ann.getCychgSc())
                .cychgScCurCd(ann.getCychgScCurCd())
                .cychgEtc(ann.getCychgEtc())
                .cychgEtcCurCd(ann.getCychgEtcCurCd()).build();


        return result;
    }

    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        try {
            NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            result = nlList.item(0).getTextContent();

        } catch (Exception e) {
            return "0";
        }
        return result;
    }

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, String childTag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

            //result += nlList.item(i).getFirstChild().getTextContent() + " ";
            result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
        }

        return result;
    }

    public void registAnn(int annNo) {
        try {

            String url = "http://apis.data.go.kr/1192000/CychgFrghtOut3/Info3?serviceKey=";
            String serviceKey = "QX%2BUMiIcizb1ZHTjEWuLYud2jyd1GRqKV3yW7%2FwHM0Fdag8L6nAKjWcr3RnbpjmnbDsxZu8PgBB7Zc8DON5M6w%3D%3D&";

            // pageNo=1&numOfRows=1&annNo=2020060071&annGb=v2(annGb -> 개정 이후)
            int pageNo = 1;
            int numOfRows = 50;
            // 공표 번호
            String annNO = Integer.toString(annNo);

            // parsing할 url 지정(API 키 포함해서)
            String url_code = url + serviceKey + "pageNo=" + Integer.toString(pageNo) + "&numOfRows=" + Integer.toString(numOfRows) + "&annNo=" + annNO + "&annGb=v2";
            System.out.println("URL = " + url_code);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new URL(url_code).openStream());
            // System.out.println(doc);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();
            // System.out.println("helloWorld!");

            // 파싱할 tag xml <item> 태그 파싱!
            NodeList nList = doc.getElementsByTagName("item");
            System.out.println(nList.getLength());
            while(nList.getLength() > 0) {

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);

                    Element eElement = (Element) nNode;

                    /*
                     * private String cychgOf;
                     * private String cychgBaf;
                     * private String cychgCaf;
                     * private String cychgLss;
                     * private String cychgEbs;
                     * private String cychgOthc;
                     * private String cychgDthc;
                     * private String cychgDf;
                     * private String cychgDo;
                     * private String cychgWaf;
                     * private String cychgSc;
                     * private String cychgEtc;
                     * DAO Build!
                     * */
                    Announ dao = Announ.builder()
                            /* ============ 필터링 부분 ============ */
                            .annNo(getTagValue("annNo", eElement))
                            .annRuteNm(getTagValue("annRuteNm", eElement))
                            .shipngPrtNm(getTagValue("shipngPrtNm", eElement))
                            .landngPrtNm(getTagValue("landngPrtNm", eElement))
                            .codPrtNm(getTagValue("codPrtNm", eElement))
                            .tsYn(getTagValue("tsYn", eElement))
                            .contnOwnSeNm(getTagValue("contnOwnSeNm", eElement))
                            .contnCndNm(getTagValue("contnCndNm", eElement))
                            .contnStdStndrdNm(getTagValue("contnStdStndrdNm", eElement))
                            .frghtNm(getTagValue("frghtNm", eElement))
                            .tnspotSeNm(getTagValue("tnspotSeNm", eElement))

                            /* ============ 금액 부분 ============ */
                            .cychgOf(Integer.parseInt(getTagValue("cychgOf", eElement)))
                            .cychgOfCurCd(getTagValue("cychgOfCurCd", eElement))
                            .cychgBaf(Integer.parseInt(getTagValue("cychgBaf", eElement)))
                            .cychgBafCurCd(getTagValue("cychgBafCurCd", eElement))
                            .cychgCaf(Integer.parseInt(getTagValue("cychgCaf", eElement)))
                            .cychgCafCurCd(getTagValue("cychgCafCurCd", eElement))
                            .cychgLss(Integer.parseInt(getTagValue("cychgLss", eElement)))
                            .cychgLssCurCd(getTagValue("cychgLssCurCd", eElement))
                            .cychgEbs(Integer.parseInt(getTagValue("cychgEbs", eElement)))
                            .cychgEbsCurCd(getTagValue("cychgEbsCurCd", eElement))
                            .cychgOthc(Integer.parseInt(getTagValue("cychgOthc", eElement)))
                            .cychgOthcCurCd(getTagValue("cychgOthcCurCd", eElement))
                            .cychgDthc(Integer.parseInt(getTagValue("cychgDthc", eElement)))
                            .cychgDthcCurCd(getTagValue("cychgDthcCurCd", eElement))
                            .cychgDf(Integer.parseInt(getTagValue("cychgDf", eElement)))
                            .cychgDfCurCd(getTagValue("cychgDfCurCd", eElement))
                            .cychgDo(Integer.parseInt(getTagValue("cychgDo", eElement)))
                            .cychgDoCurCd(getTagValue("cychgDoCurCd", eElement))
                            .cychgWaf(Integer.parseInt(getTagValue("cychgWaf", eElement)))
                            .cychgWafCurCd(getTagValue("cychgWafCurCd", eElement))
                            .cychgSc(Double.parseDouble(getTagValue("cychgSc", eElement)))
                            .cychgScCurCd(getTagValue("cychgScCurCd", eElement))
                            .cychgEtc(Integer.parseInt(getTagValue("cychgEtc", eElement)))
                            .cychgEtcCurCd(getTagValue("cychgEtcCurCd", eElement))
                            .cychgEtcDc(getTagValue("cychgEtcDc", eElement))
                            .cychgRm(getTagValue("cychgRm", eElement)).build();

                    announceRepo.save(dao);

                    System.out.println(dao);

                }
                // 다음 페이지 확인을 위한 절차.
                pageNo += 1;
                url_code = url + serviceKey + "pageNo=" + Integer.toString(pageNo) + "&numOfRows=" + Integer.toString(numOfRows) + "&annNo=" + annNO + "&annGb=v2";
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(new URL(url_code).openStream());
                doc.getDocumentElement().normalize();
                nList = doc.getElementsByTagName("item");
                System.out.println("URL = " + url_code);
                System.out.println(nList.getLength());
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
