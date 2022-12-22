package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.LogInRegisterActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.EmployeeBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkTypeViewModel;
import com.example.myapplication.spinners.CarSpinnerAdapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private EditText profile_fioTV;
    private EditText profile_loginTV;
    private EditText profile_passwordTV;
    private Button profile_button_update_data;
    private Button profile_button_excel_report;
    private Button profile_button_word_report;
    private Button profile_button_pdf_report;
    private Button profile_button_exit;
    private App app;
    private ArrayList<CarViewModel> cars;
    private CarSpinnerAdapter adapter;
    private Spinner spinner;
    private CarViewModel car;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        spinner = view.findViewById(R.id.profile_spinner_cars);
        profile_fioTV = view.findViewById(R.id.profile_fioTV);
        profile_loginTV = view.findViewById(R.id.profile_loginTV);
        profile_passwordTV = view.findViewById(R.id.profile_passwordTV);
        profile_button_update_data = view.findViewById(R.id.profile_button_update_data);
        profile_button_excel_report = view.findViewById(R.id.profile_button_excel_report);
        profile_button_word_report = view.findViewById(R.id.profile_button_word_report);
        profile_button_pdf_report = view.findViewById(R.id.profile_button_pdf_report);
        profile_button_exit = view.findViewById(R.id.profile_button_exit);

        adapter = new CarSpinnerAdapter(getContext());
        spinner.setAdapter(adapter);

        loadData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                car = (CarViewModel) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        profile_button_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        profile_button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.setEmployee(null);
                Intent intent = new Intent(getActivity(), LogInRegisterActivity.class);
                startActivity(intent);
            }
        });

        profile_button_excel_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReportWorks();
            }
        });

        profile_button_word_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReportAuto();
            }
        });

        profile_button_pdf_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReportTOs();
            }
        });
        return view;
    }

    private void loadData() {
        profile_fioTV.setText(App.getEmployee().getFIO());
        profile_loginTV.setText(App.getEmployee().getLogin());
        profile_passwordTV.setText(App.getEmployee().getPassword());

        //Подгружаем автомобили
        Call<List<CarViewModel>> call = app.getStoService().getApi().getCars();
        call.enqueue(new Callback<List<CarViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarViewModel>> call, @NonNull Response<List<CarViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                cars = (ArrayList<CarViewModel>) response.body();
                if (cars != null) {
                    adapter.setData(cars);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Привлеките клиентов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void updateProfile() {
        if (!TextUtils.isEmpty(profile_fioTV.getText())
                && !TextUtils.isEmpty(profile_loginTV.getText()) &&
                !TextUtils.isEmpty(profile_passwordTV.getText())) {
            EmployeeBindingModel model = new EmployeeBindingModel();
            model.setId(App.getEmployee().getId());
            model.setFIO(String.valueOf(profile_fioTV.getText()));
            model.setLogin(String.valueOf(profile_loginTV.getText()));
            model.setPassword(String.valueOf(profile_passwordTV.getText()));
            Call<Void> call = app.getStoService().getApi().updateProfile(model);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    App.getEmployee().setFIO(String.valueOf(profile_fioTV.getText()));
                    App.getEmployee().setLogin(String.valueOf(profile_loginTV.getText()));
                    App.getEmployee().setPassword(String.valueOf(profile_passwordTV.getText()));
                    Toast.makeText(getContext(), "Данные изменены", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
    }

    //Создание отчёта по ТО, сделанного работником
    private void createReportTOs() {
        Call<List<TOViewModel>> call = app.getStoService().getApi().getTOs(App.getEmployee().getId());
        call.enqueue(new Callback<List<TOViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TOViewModel>> call, @NonNull Response<List<TOViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<TOViewModel> tos = (ArrayList<TOViewModel>) response.body();
                try {
                    if (tos != null && tos.size() > 0)
                        createPdfReportTOS(tos);
                    else
                        Toast.makeText(getContext(), "Проведите хоть одно ТО", Toast.LENGTH_SHORT).show();
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ошибка создания отчёта", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TOViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void createPdfReportTOS( ArrayList<TOViewModel> tos) throws IOException, DocumentException {
        String filename = App.getEmployee().getFIO() + " ТО.pdf";
        Document document = new Document();
        File root = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Reports");

        if (!root.exists()) {
            root.mkdirs();
        }

        BaseFont bf = BaseFont.createFont("/assets/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12, Font.NORMAL);

        File gpxfile = new File(root, filename);  // generate pdf file in that directory

        PdfWriter.getInstance(document,new FileOutputStream(gpxfile));

        document.open();

        Paragraph p = new Paragraph("ТО работника \"" + App.getEmployee().getFIO() + "\"", font);
        document.add(p);

        Paragraph p2 = new Paragraph();
        p2.add(" ");
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(" ");
        document.add(p3);


        PdfPTable tablePdf = new PdfPTable(7);
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Номер ТО", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Автомобиль", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Статус", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Дата создания", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Дата начала", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Дата окончания", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Сумма", font));

        for(TOViewModel to : tos){
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(to.getId()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, to.getCarName(), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, to.getStatus(), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, getDateNormalFormat(to.getDateCreate()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, getDateNormalFormat(to.getDateImplement()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, getDateNormalFormat(to.getDateOver()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(to.getSum()), font));
        }

        document.add(tablePdf);
        document.addCreationDate();
        document.close();
        Toast.makeText(getContext(), "Файл успешно создан", Toast.LENGTH_SHORT).show();
    }

    //Создание отчёта по всем услугам
    private void createReportWorks() {
        Call<List<WorkTypeViewModel>> call = app.getStoService().getApi().getWorkTypes();
        call.enqueue(new Callback<List<WorkTypeViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<WorkTypeViewModel>> call, @NonNull Response<List<WorkTypeViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<WorkTypeViewModel> works = (ArrayList<WorkTypeViewModel>) response.body();
                try {
                    if (works != null && works.size() > 0)
                        createPdfReportWorks(works);
                    else
                        Toast.makeText(getContext(), "Создайте хоть одну услугу", Toast.LENGTH_SHORT).show();
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ошибка создания отчёта", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WorkTypeViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void createPdfReportWorks(ArrayList<WorkTypeViewModel> works) throws IOException, DocumentException {
        //Получение запчасти по типу услуги
        String filename = "услуги.pdf";
        Document document = new Document();
        File root = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Reports");

        if (!root.exists()) {
            root.mkdirs();
        }

        BaseFont bf = BaseFont.createFont("/assets/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12, Font.NORMAL);

        File gpxfile = new File(root, filename);  // generate pdf file in that directory

        PdfWriter.getInstance(document,new FileOutputStream(gpxfile));

        document.open();

        Paragraph p = new Paragraph("Наши услуги ", font);
        document.add(p);

        Paragraph p2 = new Paragraph();
        p2.add(" ");
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(" ");
        document.add(p3);


        PdfPTable tablePdf = new PdfPTable(6);
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Номер услуги", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Название", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Время выполнения", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Цена услуги", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Цена с уч. запчастей", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Запчасти (кол-во)", font));
        for(WorkTypeViewModel work : works){
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(work.getId()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, work.getWorkName(), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, work.getExecutionTime(), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(work.getPrice()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(work.getNetPrice()), font));
            tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(work.getWorkSpareParts().size()), font));
        }

        document.add(tablePdf);
        document.addCreationDate();
        document.close();
        Toast.makeText(getContext(), "Файл успешно создан", Toast.LENGTH_SHORT).show();
    }

    //Создание отчёта по автомобилю
    private void createReportAuto() {
        if (car != null) {
            try {
                createPdfReportAuto();
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Ошибка создания отчёта", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "Выберите автомобиль", Toast.LENGTH_SHORT).show();
        }
    }

    private void createPdfReportAuto() throws IOException, DocumentException {
        String filename = car.getBrand() + " " + car.getModel() + ".pdf";
        Document document = new Document();
        File root = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Reports");

        if (!root.exists()) {
            root.mkdirs();
        }

        BaseFont bf = BaseFont.createFont("/assets/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12, Font.NORMAL);

        File gpxfile = new File(root, filename);  // generate pdf file in that directory

        PdfWriter.getInstance(document,new FileOutputStream(gpxfile));

        document.open();

        Paragraph p = new Paragraph("Автомобиль \"" + car.getBrand() + " " + car.getModel() + "\"", font);
        document.add(p);

        Paragraph p2 = new Paragraph();
        p2.add(" ");
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(" ");
        document.add(p3);


        PdfPTable tablePdf = new PdfPTable(4);
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Марка авто", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Модель авто", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"VIN", font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Номер телефона владельца", font));

        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, String.valueOf(car.getBrand()), font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, car.getModel(), font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, car.getVIN(), font));
        tablePdf.addCell(new Phrase(Element.ALIGN_LEFT, car.getOwnerPhoneNumber(), font));

        document.add(tablePdf);

        Paragraph p4 = new Paragraph();
        p4.add(" ");
        document.add(p4);

        Paragraph p5 = new Paragraph();
        p5.add(" ");
        document.add(p5);

        Paragraph p6 = new Paragraph();
        p4.add("Записи");
        document.add(p6);

        for(Map.Entry<Integer, String> record : car.getRecords().entrySet()){
            Paragraph p7 = new Paragraph();
            p7.add(record.getValue());
            document.add(p7);
        }

        document.addCreationDate();
        document.close();
        Toast.makeText(getContext(), "Файл успешно создан", Toast.LENGTH_SHORT).show();
    }

    private String getDateNormalFormat(String s) {
        if (!s.equals("-"))
            return s.split("T")[0] + " " + s.split("T")[1].substring(0, 5);
        return "-";
    }
}