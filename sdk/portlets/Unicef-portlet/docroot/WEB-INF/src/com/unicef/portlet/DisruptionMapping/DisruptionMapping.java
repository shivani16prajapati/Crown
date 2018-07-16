package com.unicef.portlet.DisruptionMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.portlet.PortletContext;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller(value = "DisruptionMapping")
@RequestMapping("VIEW")
/**
 * Portlet implementation class DisruptionMapping
 */
public class DisruptionMapping {

	@RenderMapping
	public String handleRenderRequest(RenderRequest request,
			RenderResponse response, Model model) {
		model.addAttribute("currentDate", new Date());
		return "disruption";
	}

	/*@ResourceMapping(value = "resourceUrl2")
	public void ExportJsonToExcel(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) {
		PortletContext context = resourceRequest.getPortletSession()
				.getPortletContext();
		String filePath = context.getRealPath("/");
		_log.info("-------------CALL EXPORT FUNCTION--------------------");
		String data = ParamUtil.getString(resourceRequest, "data1");
		JSONArray jsonArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		try {
			jsonArray = (JSONArray) jsonParser.parse(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("BlockChain Data");
		org.apache.poi.ss.usermodel.Font font = workbook.createFont();
		font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
		Row row;
		row = sheet.createRow(0);
		Cell cell;
		XSSFRichTextString s1;
		cell = row.createCell(0);
		s1 = new XSSFRichTextString("Name");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(1);
		s1 = new XSSFRichTextString("Short Description");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(2);
		s1 = new XSSFRichTextString("Full Description");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(3);
		s1 = new XSSFRichTextString("Primary Disruption");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(4);
		s1 = new XSSFRichTextString("Secondary Disruption");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(5);
		s1 = new XSSFRichTextString("Impact On Primary");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(6);
		s1 = new XSSFRichTextString("Scale On Primary");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(7);
		s1 = new XSSFRichTextString("Impact on Secondary");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(8);
		s1 = new XSSFRichTextString("Scale on Secondary");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(9);
		s1 = new XSSFRichTextString("Source");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(10);
		s1 = new XSSFRichTextString("Source Reliability");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(11);
		s1 = new XSSFRichTextString("Likelihood");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(12);
		s1 = new XSSFRichTextString("Invention Date");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(13);
		s1 = new XSSFRichTextString("Date of Initial Uptake");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(14);
		s1 = new XSSFRichTextString("Time to Mainstream Uptake");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(15);
		s1 = new XSSFRichTextString("Trough Timeframe");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(16);
		s1 = new XSSFRichTextString("Scale");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(17);
		s1 = new XSSFRichTextString("Type");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(18);
		s1 = new XSSFRichTextString("Sector");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(19);
		s1 = new XSSFRichTextString("Downstream Cascades2");
		s1.applyFont(font);
		cell.setCellValue(s1);

		cell = row.createCell(20);
		s1 = new XSSFRichTextString("Image URL");
		s1.applyFont(font);
		cell.setCellValue(s1);

		for (int i = 1; i < jsonArray.size(); i++) {
			row = sheet.createRow(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject = (JSONObject) jsonArray.get(i);
			cell = row.createCell(0);
			
			String Name = (String) jsonObject.get("name");
			cell.setCellValue(Name);

			cell = row.createCell(1);
			String ShortDescription = (String) jsonObject
					.get("ShortDescription");
			cell.setCellValue(ShortDescription);

			cell = row.createCell(2);
			String FullDescription = (String) jsonObject.get("FullDescription");
			cell.setCellValue(FullDescription);

			cell = row.createCell(3);
			String PrimaryDisruption = (String) jsonObject
					.get("PrimaryDisruption");
			cell.setCellValue(PrimaryDisruption);

			cell = row.createCell(4);
			String SecondaryDisruption = (String) jsonObject
					.get("SecondaryDisruption");
			cell.setCellValue(SecondaryDisruption);

			cell = row.createCell(5);
			String ImpactOnPrimary = (String) jsonObject.get("impact");
			cell.setCellValue(ImpactOnPrimary);

			cell = row.createCell(6);
			String ScaleOnPrimary = (String) jsonObject.get("scale");
			cell.setCellValue(ScaleOnPrimary);

			cell = row.createCell(7);
			String ImpactonSecondary = (String) jsonObject
					.get("ImpactonSecondary");
			cell.setCellValue(ImpactonSecondary);

			cell = row.createCell(8);
			String ScaleonSecondary = (String) jsonObject
					.get("ScaleonSecondary");
			cell.setCellValue(ScaleonSecondary);

			cell = row.createCell(9);
			String Source = (String) jsonObject.get("Source");
			cell.setCellValue(Source);

			cell = row.createCell(10);
			if (jsonObject.get("SourceReliability") instanceof Number) {
				Long SourceReliability = ((Number) jsonObject
						.get("SourceReliability")).longValue();
				cell.setCellValue(SourceReliability);

			}
			if (jsonObject.get("SourceReliability") instanceof String) {
				String SourceReliability = (String) jsonObject
						.get("SourceReliability");
				cell.setCellValue(SourceReliability);

			}

			cell = row.createCell(11);
			if (jsonObject.get("Likelihood") instanceof Number) {
				if (jsonObject.get("Likelihood") instanceof Long) {
					Long Likelihood = ((Number) jsonObject.get("Likelihood"))
							.longValue();
					cell.setCellValue(Likelihood);

				} else {
					Double Likelihood = ((Number) jsonObject.get("Likelihood"))
							.doubleValue();
					cell.setCellValue(Likelihood);

				}
			}
			if (jsonObject.get("Likelihood") instanceof String) {
				String Likelihood = (String) jsonObject.get("Likelihood");
				cell.setCellValue(Likelihood);

			}

			cell = row.createCell(12);
			String InventionDate = (String) jsonObject.get("date");
			cell.setCellValue(InventionDate);

			cell = row.createCell(13);
			if (jsonObject.get("DateofInitialUptake") instanceof Number) {
				Long DateofInitialUptake = ((Number) jsonObject
						.get("DateofInitialUptake")).longValue();
				cell.setCellValue(DateofInitialUptake);

			}
			if (jsonObject.get("DateofInitialUptake") instanceof String) {
				String DateofInitialUptake = (String) jsonObject
						.get("DateofInitialUptake");
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(DateofInitialUptake);
			}

			cell = row.createCell(14);
			if (jsonObject.get("TimetoMainstreamUptake") instanceof Number) {
				Long TimetoMainstreamUptake = ((Number) jsonObject
						.get("TimetoMainstreamUptake")).longValue();
				cell.setCellValue(TimetoMainstreamUptake);

			}
			if (jsonObject.get("TimetoMainstreamUptake") instanceof String) {
				String TimetoMainstreamUptake = (String) jsonObject
						.get("TimetoMainstreamUptake");
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(TimetoMainstreamUptake);

			}

			cell = row.createCell(15);
			if((jsonObject.get("TimetoMainstreamUptake") instanceof String) && 
					(jsonObject.get("DateofInitialUptake") instanceof String)){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue("");
			}
			else{
				cell.setCellType(Cell.CELL_TYPE_FORMULA);
				cell.setCellFormula("(O" + (i + 1) + "-N" + (i + 1) + ")");
			}
			cell = row.createCell(16);
			String Scale = (String) jsonObject.get("Scale");
			cell.setCellValue(Scale);

			cell = row.createCell(17);
			String Type = (String) jsonObject.get("Type");
			cell.setCellValue(Type);

			cell = row.createCell(18);
			String Sector = (String) jsonObject.get("Sector");
			cell.setCellValue(Sector);

			cell = row.createCell(19);
			String DownstreamCascades2 = (String) jsonObject
					.get("DownstreamCascades2");
			cell.setCellValue(DownstreamCascades2);

			cell = row.createCell(20);
			String ImageURL = (String) jsonObject.get("image");
			cell.setCellValue(ImageURL);
		}
		try {
			for(int i=0;i<21;i++){
				sheet.autoSizeColumn(i);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			*/
			/*FileOutputStream output_file = new FileOutputStream(filePath
					+ "/json/Updated Blockchain Level 2.xlsx");
			workbook.write(output_file);
			output_file.close();*/
			//resourceResponse.setContentType("application/vnd.ms-excel");
			//resourceResponse.setProperty("Content-Disposition", "attachment; filename=\""+ "Updated Blockchain Level 2.xlsx" + "\"");
			//resourceResponse.getWriter().write(filePath);
		/*	PortletResponseUtil.sendFile(resourceRequest, resourceResponse, "Blockchain Level 2.xlsx", out.toByteArray(), "application/vnd.ms-excel");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	*/

	@ResourceMapping(value = "resourceUrl1")
	public void ConvertDataFromXLsToJson(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) {
		PortletContext context = resourceRequest.getPortletSession()
				.getPortletContext();
		String filePath = context.getRealPath("/json/Blockchain Level 2.xlsx");
		_log.info("filePath--------------: " + filePath);
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			JSONArray json = new JSONArray();
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			JSONObject rows = new JSONObject();
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				boolean hasData = true;
				Row row = rowIterator.next();
				rows = new JSONObject();
				Iterator<Cell> cellIterator = row.cellIterator();
				boolean isEmptyRow = checkIfRowIsEmpty(row);
				if (!isEmptyRow) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getRowIndex() == 0) {
							hasData = false;
							break;
						}
						if (cell.getColumnIndex() <= 20) {
							hasData = true;
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								rows.put(
										(cell.getSheet()
												.getRow(0)
												.getCell(
														(cell.getColumnIndex()))
												.getRichStringCellValue()
												.toString()).replaceAll("\\s",
												""), cell.getStringCellValue());
								break;

							case Cell.CELL_TYPE_NUMERIC:
								if (cell.getColumnIndex() == 12) {
									try {
										SimpleDateFormat sdf = new SimpleDateFormat(
												"MM/dd/yyyy");
										String date = sdf.format(cell
												.getDateCellValue());
										rows.put(
												(cell.getSheet()
														.getRow(0)
														.getCell(
																(cell.getColumnIndex()))
														.getRichStringCellValue()
														.toString())
														.replaceAll("\\s", ""),
												date);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									rows.put((cell.getSheet().getRow(0)
											.getCell((cell.getColumnIndex()))
											.getRichStringCellValue()
											.toString()).replaceAll("\\s", ""),
											cell.getNumericCellValue());
								}
								break;

							case Cell.CELL_TYPE_BLANK:
								rows.put(
										(cell.getSheet()
												.getRow(0)
												.getCell(
														(cell.getColumnIndex()))
												.getRichStringCellValue()
												.toString()).replaceAll("\\s",
												""), "");
								break;
							}
						}
					}
				} else {
					hasData = false;
				}
				if (hasData == true)
					json.add(rows);
			}
			file.close();
			resourceResponse.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		boolean isEmptyRow = true;
		for (int cellNum = row.getFirstCellNum(); cellNum < row
				.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK
					&& StringUtils.isNotBlank(cell.toString())) {
				isEmptyRow = false;
			}
		}
		return isEmptyRow;
	}

	private static final Log _log = LogFactoryUtil
			.getLog(DisruptionMapping.class.getName());
}
