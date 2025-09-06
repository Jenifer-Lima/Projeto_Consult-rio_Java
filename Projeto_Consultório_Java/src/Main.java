/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Projeto_Consutório;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author jenif
 */
public class Main extends JFrame{
    
    private JButton btCadastrarMedico;
    private JButton btCadastrarPaciente;
    private JButton btCadastrarConsulta;
    private JButton btCancelarConsulta;
    private JButton btRelatorio;
    
    static ArrayList<Medico> listaMedicos = new ArrayList<>();
    static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    static ArrayList<Consulta> listaConsultas = new ArrayList<>();
    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    public Main(){
    this.setTitle("Consultório");
    setBounds(0, 0, 240, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().setLayout(null);
    
    btCadastrarMedico = new JButton();
    btCadastrarMedico.setText("Cadastrar Médicos");
    btCadastrarMedico.setBounds(10, 10, 200, 50);
    this.add(btCadastrarMedico);
    
    btCadastrarPaciente = new JButton();
    btCadastrarPaciente.setText("Cadastrar Paciente");
    btCadastrarPaciente.setBounds(10, 70, 200, 50);
    this.add(btCadastrarPaciente);
    
    btCadastrarConsulta = new JButton();
    btCadastrarConsulta.setText("Cadastrar Consulta");
    btCadastrarConsulta.setBounds(10, 130, 200, 50);
    this.add(btCadastrarConsulta);
    
    btCancelarConsulta = new JButton();
    btCancelarConsulta.setText("Cancelar Consulta");
    btCancelarConsulta.setBounds(10, 190, 200, 50);
    this.add(btCancelarConsulta);
    
    btRelatorio = new JButton();
    btRelatorio.setText("Relatório Financeiro");
    btRelatorio.setBounds(10, 250, 200, 50);
    this.add(btRelatorio);
    
    btCadastrarMedico.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            Medico medico = new Medico();
            medico.setCrm(JOptionPane.showInputDialog("Informe o CRM"));
            medico.setNome(JOptionPane.showInputDialog("Informe o nome"));
            medico.setDataNascimento(JOptionPane.showInputDialog("Data de Nascimento [dd/MM/yyyy]"));
            Date dataCadastro = new Date();
            medico.setDataCadastro(dataCadastro);
            JOptionPane.showMessageDialog(null, "Cadastro de Médico realizado com sucesso", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            listaMedicos.add(medico);
        }
    });
    
    btCadastrarPaciente.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            Paciente paciente = new Paciente();
            paciente.setCpf(JOptionPane.showInputDialog("Cpf:"));
            paciente.setNome(JOptionPane.showInputDialog("Nome"));
            paciente.setDataNascimento(JOptionPane.showInputDialog("Data de Nascimento"));
            Date dataCadastro = new Date();
            paciente.setDataCadastro(dataCadastro);
            JOptionPane.showMessageDialog(null, "Cadastro de Paciente realizado com sucesso!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            listaPacientes.add(paciente);
        }
    });
    
    btCadastrarConsulta.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            Consulta consulta = new Consulta();
            String crmMedico = JOptionPane.showInputDialog("Crm do Médico");
            if(validacaoCrm(crmMedico)){
                consulta.setCrmMedico(crmMedico);
                String cpfPaciente = JOptionPane.showInputDialog("CFP do Paciente");
                if(validacaoCpf(cpfPaciente)){
                    consulta.setCpfPaciente(cpfPaciente);
                    consulta.setDataConsulta(JOptionPane.showInputDialog("Data da Consulta [dd/MM/yyyy]"));
                    consulta.setHoraConsulta(JOptionPane.showInputDialog("Hora da Consulta"));
                    consulta.setStatus("Agendada");
                    if(validacaoPrimeiraConsulta(cpfPaciente)){
                        consulta.setValor(300);
                        listaConsultas.add(consulta);
                        JOptionPane.showMessageDialog(null, "Valor:\nR$" + consulta.getValor());
                    }
                    else{
                        consulta.setValor(350);
                        listaConsultas.add(consulta);
                        JOptionPane.showMessageDialog(null, "Valor:\nR$" + consulta.getValor());
                    }
                    
                }
                else{
                    JOptionPane.showMessageDialog(null, "CPF não cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Esse CRM não está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "Médicos Cadastrados: \n" + mostrarMedicos());
            }
            
        }
    });
    
    btCancelarConsulta.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            String cpfPaciente = JOptionPane.showInputDialog("CPF do Paciente");
            String crmMedico = JOptionPane.showInputDialog("CRM do Médico");
            String dataConsulta = JOptionPane.showInputDialog("Data da Consulta");
            if(validacaoConsultaMarcada(crmMedico, cpfPaciente, dataConsulta)){
                JOptionPane.showMessageDialog(null, "Consulta cancelada com sucesso!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Essa Consulta não pode ser cancelada pois não foi agendada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }       
    });
    
    btRelatorio.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            float valorTotal = 0;
            String dados = "";
            int verificador = 0;
            String data = JOptionPane.showInputDialog(null, "Datas com consultas, escolha uma para ver o relatório financeiro da data\n\n" + mostrarDatas());
            for (Consulta consulta : listaConsultas){
                if(consulta.getDataConsulta().equalsIgnoreCase(data)){
                    valorTotal += consulta.getValor();
                    dados += "CRM: " + consulta.getCrmMedico() + "\nCPF: " + consulta.getCpfPaciente() + "\nValor: R$" + consulta.getValor() + "\n\n";
                    verificador += 1;
                }
            }
            if(verificador == 0){
                JOptionPane.showMessageDialog(null, "Não há consultas nessa data!\nValor total do dia: R$00,00", "0 consultas", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Relatório financeiro do dia\n\n" + dados + "\n\n Valor total do dia: R$" + valorTotal);
            }
        }
    });
    }
    
    public static boolean validacaoCrm(String crmMedico){
        for(Medico medico : listaMedicos){
            if(medico.getCrm().equalsIgnoreCase(crmMedico)){
                return true;
            }
            }
        return false;
        }
    
    public static boolean validacaoCpf(String cpfPaciente){
        for(Paciente paciente : listaPacientes){
            if(paciente.getCpf().equalsIgnoreCase(cpfPaciente)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean validacaoPrimeiraConsulta(String cpfPaciente){
        for(Consulta consulta : listaConsultas){
            if(consulta.getCpfPaciente().equalsIgnoreCase(cpfPaciente)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean validacaoConsultaMarcada(String crm, String cpf, String data){
        for(Consulta consulta : listaConsultas){
            if(consulta.getCrmMedico().equalsIgnoreCase(crm) & consulta.getCpfPaciente().equalsIgnoreCase(cpf) & consulta.getDataConsulta().equalsIgnoreCase(data)){
                consulta.setStatus("Cancelada");
                consulta.setValor(0);
                return true;
            }
        }
        return false;
    }
    
    public static String mostrarMedicos(){
        String medicos = "";
        for(Medico medico : listaMedicos){
            medicos += "Nome: " + medico.getNome().toUpperCase() + "\nCRM: " + medico.getCrm() + "\n\n";
        }
        return medicos;
    }
    
    public static String mostrarDatas(){
        ArrayList<String> listaDatas = new ArrayList<>();
        String datas = "";
        for(Consulta consulta : listaConsultas){
            if(!datas.contains(consulta.getDataConsulta())){
                datas += consulta.getDataConsulta() + "\n";
            }
            
        }
        return datas;
    }
    
    public static void main(String[] args) {
        Main menu = new Main();
        menu.setVisible(true);
    }
    
}
