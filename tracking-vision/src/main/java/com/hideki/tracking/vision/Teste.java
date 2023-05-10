/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hideki.tracking.vision;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.sistema.Sistema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hidek
 */
public class Teste {

    public static void main(String[] args) {
        API api = new API();
        Conexao conexao = new Conexao();
        Dados dado = new Dados();
        List<Dados> listaDados = new ArrayList();

        
        JdbcTemplate con = conexao.getConnection();

        // API
        // DADOS MONITORADOS
//        System.out.println("Bytes de leitura do disco: " + api.getBystesDeLeitura());
//        System.out.println("Bytes de Escrita do disco: " + api.getBytesDeEscrita());
//        System.out.println("Memoria em uso: " + api.getMemoriaEmUso());
//        System.out.println("Processador em uso: " + api.getProcessadorEmUso());
//        System.out.println("Bytes Enviados pela rede: " + api.getBytesEnviados());
//        System.out.println("Bytes Recebidos pela rede: " + api.getBytesRecebidos());
//        System.out.println("ID da Janela: " + api.getJanelaId() + "\n");
//
//        System.out.println("\n" + dado);
        
        // INFORMAÇÕES HARDWARE
//        System.out.println("\nINFORMAÇÕES HARDWARE: " + api.getHardware());
//        // INFORMAÇÕES PROCESSOS
//        System.out.println("\nINFORMAÇÕES PROCESSOS: " + api.getProcesso());

        // BANCO DE DADOS
//        con.update("INSERT INTO ---------- VALUES (?, ?);"); //, teste.getNome(), teste.getAnoLancamento());
//        listaDados = con.query("SELECT * FROM -------;", new BeanPropertyRowMapper(Dados.class));

//        System.out.println(listaDados);





        MaquinaService maquinaService = new MaquinaService();
        LogService logService = new LogService();
        List<String> janelas = new ArrayList();
        List<Long> janelasPid = new ArrayList();

        //Objeto do tipo Looca
        Looca looca = new Looca();

        //Pegando o grupo de janelas do looca
        JanelaGrupo janelaGrupo = looca.getGrupoDeJanelas();

        //Frequncia do processador convertida para GHz
        Double frequenciaCpu = Double.valueOf(api.getProcessador().getFrequencia());
        frequenciaCpu = frequenciaCpu / 1000000000.00;


        Double capRam = Double.valueOf(api.getMemoria().getTotal());
        capRam = capRam / 1073741824.00;

        Double capDisco = Double.valueOf(api.getDisco().get(0).getTamanho());
        capDisco = capDisco / 1073741824.00;

        Double leituraDisco = Double.valueOf(api.getDisco().get(0).getBytesDeLeitura());
        leituraDisco = leituraDisco / 100000000.00;
        System.out.println(leituraDisco);

        Double escritaDisco = Double.valueOf(api.getDisco().get(0).getBytesDeEscritas());
        escritaDisco = escritaDisco / 100000000.00;
        System.out.println(escritaDisco);

        //Insert na tabela maquina
        Rede rede = looca.getRede();

        DiscoGrupo disco = looca.getGrupoDeDiscos();


        System.out.println(rede.getGrupoDeInterfaces().getInterfaces());
        System.out.println(rede.getParametros());

        Maquina maquina = new Maquina(null,rede.getParametros().getHostName(),1,api.getProcessador().getNome(),frequenciaCpu,"Memoria",capRam,api.getDisco().get(0).getModelo(),capDisco,leituraDisco,escritaDisco,1,1);

        List<Maquina> hostname = maquinaService.buscarPeloHostname(rede.getParametros().getHostName());
        System.out.println("ID " +hostname.get(0).getIdMaquina());
        if(hostname.size() == 0) {
            maquinaService.salvarMaquina(maquina);
        }else {
            System.out.println("Maquina Ja cadastrada ou houve algum erro");
        }


        // adicionando as janelas a uma lista e seus respectivos pid
        for (int i = 0; i < janelaGrupo.getTotalJanelas(); i++) {
            if(janelaGrupo.getJanelas().get(i).getTitulo().length() > 0) {
                janelas.add(janelaGrupo.getJanelas().get(i).getTitulo());
                janelasPid.add(janelaGrupo.getJanelas().get(i).getPid());
            }
        }
                
                
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        //Insert na tabela log
        Double usoDisco = Double.valueOf(api.getDisco().get(0).getTamanho()-disco.getVolumes().get(0).getDisponivel());
        usoDisco = usoDisco / 1073741824.00;

        Double usoRam = Double.valueOf(api.getMemoriaEmUso());
        usoRam = usoRam / 1073741824.00;


        for (int j = 0; j < janelas.size(); j++) {

//            Log log = new Log(null, timeStamp, janelasPid.get(j),janelas.get(j) , api.getProcessador().getUso(),usoDisco ,usoRam,maquina.getIdMaquina(),1);
//            logService.salvarLog(log);
        }



    }

}
