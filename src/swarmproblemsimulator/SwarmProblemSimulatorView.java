/*
 * SwarmProblemSimulatorView.java
 */

package swarmproblemsimulator;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.VetoableChangeListenerProxy;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 * The application's main frame.
 */
public class SwarmProblemSimulatorView extends FrameView {

    public SwarmProblemSimulatorView(SingleFrameApplication app) {
        super(app);

        initComponents();
        //TiledDiscreteSpace space=((SwarmProblemSimulatorApp)app).space;
        //Vector<Ant> ants=((SwarmProblemSimulatorApp)app).ants;
        dsview= new GridLayoutDSView(20,20,100,1337);//default 20x20, 100 ants, seed is 1337
        mainScrollPane.setViewportView(dsview);
        actionMap = org.jdesktop.application.Application.getInstance(swarmproblemsimulator.SwarmProblemSimulatorApp.class).getContext().getActionMap(SwarmProblemSimulatorView.class, this);
        stepTimer = new Timer(500, actionMap.get("stepFoward"));
        //stepDelaySpinner.setModel(new SpinnerNumberModel(.1, .01, 1.0, -.01));
        stepDelaySpinner.addChangeListener(new ChangeListener() {


            public void stateChanged(ChangeEvent e)
              {
                stepTimer.setDelay((int)Math.round(((Double)((JSpinner)e.getSource()).getValue())*1000));
              }
        });

        
        dsview.addMouseListenerToChildren(addFood);

        gridHeightSpinner.setValue(dsview.getSpace().getYwidth());
        gridWidthSpinner.setValue(dsview.getSpace().getXwidth());
        antCountSpinner.setValue(dsview.getAnts().size());
        ((CardLayout)antOptionsPanels.getLayout()).show(antOptionsPanels, "TwoChoiceAnt");


        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SwarmProblemSimulatorApp.getApplication().getMainFrame();
            aboutBox = new SwarmProblemSimulatorAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SwarmProblemSimulatorApp.getApplication().show(aboutBox);
    }

    @Action
    public void stepFoward(ActionEvent e)
      {
          
        //actionMap.get("stepFowardRun").actionPerformed(e);
         for ( Ant ant : dsview.getAnts())
            {
              ant.move();

            }
          for ( DiscreteSpaceNode node : dsview.getSpace().getSpace().values() )
            {
              PheremoneDSN pNode = (PheremoneDSN)node;
              double newLevel =(pNode.getPheremoneLevel()*(1.0-(Double)pDecaySpinner.getValue()));
              //newLevel = newLevel<1?0:newLevel;
              pNode.setPheremoneLevel(newLevel);
            }

        dsview.invalidate();
        dsview.repaint();
      }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        variablesPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        gridWidthLabel = new javax.swing.JLabel();
        gridWidthSpinner = new javax.swing.JSpinner();
        gridHeightLabel = new javax.swing.JLabel();
        gridHeightSpinner = new javax.swing.JSpinner();
        antCountLabel = new javax.swing.JLabel();
        antCountSpinner = new javax.swing.JSpinner();
        wrapEdgesCB = new javax.swing.JCheckBox();
        spacer5 = new javax.swing.JLabel();
        spacer6 = new javax.swing.JLabel();
        seedSpinner = new javax.swing.JSpinner();
        maxAntsLabel = new javax.swing.JLabel();
        maxAntsSpinner = new javax.swing.JSpinner();
        seedButton = new javax.swing.JButton();
        antTypeBox = new javax.swing.JComboBox();
        antTypeBoxLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        clickDepositSpinnerLabel = new javax.swing.JLabel();
        clickDepositSpinner = new javax.swing.JSpinner();
        jSeparator2 = new javax.swing.JSeparator();
        randFoodAmntLabel = new javax.swing.JLabel();
        randFoodAmntSpinner = new javax.swing.JSpinner();
        randFoodAmntVarLabel = new javax.swing.JLabel();
        randFoodAmntVarSpinner = new javax.swing.JSpinner();
        randFoodQntLabel = new javax.swing.JLabel();
        randFoodQntSpinner = new javax.swing.JSpinner();
        randFoodQntVarLabel = new javax.swing.JLabel();
        randFoodQntVarSpinner = new javax.swing.JSpinner();
        depositRandomFood = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        adjConstantsPanel = new javax.swing.JPanel();
        pDecaySpinner = new javax.swing.JSpinner();
        pDecaySpinnerLabel = new javax.swing.JLabel();
        antOptionsPanels = new javax.swing.JPanel();
        twoCoiceAntOptionsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        divergenceSpinner = new javax.swing.JSpinner();
        nChoiceAntOptionsPanel = new javax.swing.JPanel();
        pherLadenAmntLabel = new javax.swing.JLabel();
        pherLadenAmntSpinner = new javax.swing.JSpinner();
        mainControlPanel = new javax.swing.JPanel();
        viewScaleSlider = new javax.swing.JSlider();
        stepDelayLabel = new javax.swing.JLabel();
        stepDelaySpinner = new javax.swing.JSpinner();
        stepButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        mainScrollPane = new javax.swing.JScrollPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

        variablesPanel.setName("variablesPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(swarmproblemsimulator.SwarmProblemSimulatorApp.class).getContext().getResourceMap(SwarmProblemSimulatorView.class);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        gridWidthLabel.setText(resourceMap.getString("gridWidthLabel.text")); // NOI18N
        gridWidthLabel.setName("gridWidthLabel"); // NOI18N

        gridWidthSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(5), Integer.valueOf(5), null, Integer.valueOf(1)));
        gridWidthSpinner.setName("gridWidthSpinner"); // NOI18N

        gridHeightLabel.setLabelFor(gridHeightSpinner);
        gridHeightLabel.setText(resourceMap.getString("gridHeightLabel.text")); // NOI18N
        gridHeightLabel.setName("gridHeightLabel"); // NOI18N

        gridHeightSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(5), Integer.valueOf(5), null, Integer.valueOf(1)));
        gridHeightSpinner.setName("gridHeightSpinner"); // NOI18N

        antCountLabel.setText(resourceMap.getString("antCountLabel.text")); // NOI18N
        antCountLabel.setName("antCountLabel"); // NOI18N

        antCountSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(3), null, Integer.valueOf(1)));
        antCountSpinner.setName("antCountSpinner"); // NOI18N

        wrapEdgesCB.setText(resourceMap.getString("wrapEdgesCB.text")); // NOI18N
        wrapEdgesCB.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        wrapEdgesCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        wrapEdgesCB.setName("wrapEdgesCB"); // NOI18N

        spacer5.setText(resourceMap.getString("spacer5.text")); // NOI18N
        spacer5.setName("spacer5"); // NOI18N

        spacer6.setText(resourceMap.getString("spacer6.text")); // NOI18N
        spacer6.setName("spacer6"); // NOI18N

        seedSpinner.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(1337L), null, null, Long.valueOf(42L)));
        seedSpinner.setName("seedSpinner"); // NOI18N

        maxAntsLabel.setLabelFor(maxAntsSpinner);
        maxAntsLabel.setText(resourceMap.getString("maxAntsLabel.text")); // NOI18N
        maxAntsLabel.setName("maxAntsLabel"); // NOI18N

        maxAntsSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(10), Integer.valueOf(1), null, Integer.valueOf(1)));
        maxAntsSpinner.setName("maxAntsSpinner"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(swarmproblemsimulator.SwarmProblemSimulatorApp.class).getContext().getActionMap(SwarmProblemSimulatorView.class, this);
        seedButton.setAction(actionMap.get("randomizeSeedSpinner")); // NOI18N
        seedButton.setText(resourceMap.getString("seedButton.text")); // NOI18N
        seedButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        seedButton.setBorderPainted(false);
        seedButton.setDefaultCapable(false);
        seedButton.setFocusPainted(false);
        seedButton.setFocusable(false);
        seedButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        seedButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        seedButton.setName("seedButton"); // NOI18N

        antTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Two Choice Ant (top/right)", "N Choice Ant (top/right/bottom)" }));
        antTypeBox.setName("antTypeBox"); // NOI18N

        antTypeBoxLabel.setLabelFor(antTypeBox);
        antTypeBoxLabel.setText(resourceMap.getString("antTypeBoxLabel.text")); // NOI18N
        antTypeBoxLabel.setName("antTypeBoxLabel"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(gridWidthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gridWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spacer6, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gridHeightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gridHeightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(seedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(seedSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                    .addComponent(wrapEdgesCB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(antCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(antCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spacer5, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(maxAntsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(maxAntsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(antTypeBoxLabel)
                        .addGap(12, 12, 12)
                        .addComponent(antTypeBox, 0, 282, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(gridWidthLabel)
                    .addComponent(gridHeightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridHeightLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(seedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seedButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wrapEdgesCB)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(antCountLabel)
                    .addComponent(antCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxAntsLabel)
                    .addComponent(spacer5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxAntsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antTypeBoxLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setAction(actionMap.get("regenerateGrid")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        clickDepositSpinnerLabel.setLabelFor(clickDepositSpinner);
        clickDepositSpinnerLabel.setText(resourceMap.getString("clickDepositSpinnerLabel.text")); // NOI18N
        clickDepositSpinnerLabel.setName("clickDepositSpinnerLabel"); // NOI18N

        clickDepositSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(0), null, Integer.valueOf(100)));
        clickDepositSpinner.setName("clickDepositSpinner"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        randFoodAmntLabel.setLabelFor(randFoodAmntSpinner);
        randFoodAmntLabel.setText(resourceMap.getString("randFoodAmntLabel.text")); // NOI18N
        randFoodAmntLabel.setName("randFoodAmntLabel"); // NOI18N

        randFoodAmntSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(0), null, Integer.valueOf(10)));
        randFoodAmntSpinner.setName("randFoodAmntSpinner"); // NOI18N

        randFoodAmntVarLabel.setLabelFor(randFoodAmntVarSpinner);
        randFoodAmntVarLabel.setText(resourceMap.getString("randFoodAmntVarLabel.text")); // NOI18N
        randFoodAmntVarLabel.setName("randFoodAmntVarLabel"); // NOI18N

        randFoodAmntVarSpinner.setModel(new javax.swing.SpinnerNumberModel(0.25d, 0.0d, 1.0d, 0.01d));
        randFoodAmntVarSpinner.setName("randFoodAmntVarSpinner"); // NOI18N

        randFoodQntLabel.setLabelFor(randFoodQntSpinner);
        randFoodQntLabel.setText(resourceMap.getString("randFoodQntLabel.text")); // NOI18N
        randFoodQntLabel.setName("randFoodQntLabel"); // NOI18N

        randFoodQntSpinner.setModel(new javax.swing.SpinnerNumberModel(5, 0, 100, 1));
        randFoodQntSpinner.setName("randFoodQntSpinner"); // NOI18N

        randFoodQntVarLabel.setLabelFor(randFoodQntVarSpinner);
        randFoodQntVarLabel.setText(resourceMap.getString("randFoodQntVarLabel.text")); // NOI18N
        randFoodQntVarLabel.setName("randFoodQntVarLabel"); // NOI18N

        randFoodQntVarSpinner.setModel(new javax.swing.SpinnerNumberModel(0.25d, 0.0d, 1.0d, 0.01d));
        randFoodQntVarSpinner.setName("randFoodQntVarSpinner"); // NOI18N

        depositRandomFood.setAction(actionMap.get("depositFood")); // NOI18N
        depositRandomFood.setText(resourceMap.getString("depositRandomFood.text")); // NOI18N
        depositRandomFood.setName("depositRandomFood"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(clickDepositSpinnerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                                .addComponent(clickDepositSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(randFoodAmntLabel)
                                    .addComponent(randFoodQntLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(randFoodQntSpinner)
                                    .addComponent(randFoodAmntSpinner))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(randFoodAmntVarLabel)
                                    .addComponent(randFoodQntVarLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(randFoodAmntVarSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(randFoodQntVarSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(depositRandomFood, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(clickDepositSpinnerLabel)
                    .addComponent(clickDepositSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(randFoodAmntLabel)
                            .addComponent(randFoodAmntSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(randFoodQntLabel)
                            .addComponent(randFoodQntSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(randFoodAmntVarLabel)
                            .addComponent(randFoodAmntVarSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(randFoodQntVarLabel)
                            .addComponent(randFoodQntVarSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(depositRandomFood))
        );

        adjConstantsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("adjConstantsPanel.border.title"))); // NOI18N
        adjConstantsPanel.setName("adjConstantsPanel"); // NOI18N

        pDecaySpinner.setModel(new javax.swing.SpinnerNumberModel(0.03d, 0.0d, 1.0d, 0.01d));
        pDecaySpinner.setName("pDecaySpinner"); // NOI18N

        pDecaySpinnerLabel.setLabelFor(pDecaySpinner);
        pDecaySpinnerLabel.setText(resourceMap.getString("pDecaySpinnerLabel.text")); // NOI18N
        pDecaySpinnerLabel.setName("pDecaySpinnerLabel"); // NOI18N

        antOptionsPanels.setName("antOptionsPanels"); // NOI18N
        antOptionsPanels.setLayout(new java.awt.CardLayout());

        twoCoiceAntOptionsPanel.setName("twoCoiceAntOptionsPanel"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        divergenceSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(1.2d), Double.valueOf(0.0d), null, Double.valueOf(0.01d)));
        divergenceSpinner.setName("divergenceSpinner"); // NOI18N
        divergenceSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                divergenceSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout twoCoiceAntOptionsPanelLayout = new javax.swing.GroupLayout(twoCoiceAntOptionsPanel);
        twoCoiceAntOptionsPanel.setLayout(twoCoiceAntOptionsPanelLayout);
        twoCoiceAntOptionsPanelLayout.setHorizontalGroup(
            twoCoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, twoCoiceAntOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divergenceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        twoCoiceAntOptionsPanelLayout.setVerticalGroup(
            twoCoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(twoCoiceAntOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(twoCoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(divergenceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        antOptionsPanels.add(twoCoiceAntOptionsPanel, "TwoChoiceAnt");

        nChoiceAntOptionsPanel.setName("nChoiceAntOptionsPanel"); // NOI18N

        pherLadenAmntLabel.setText(resourceMap.getString("pherLadenAmntLabel.text")); // NOI18N
        pherLadenAmntLabel.setName("pherLadenAmntLabel"); // NOI18N

        pherLadenAmntSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(10.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        pherLadenAmntSpinner.setName("pherLadenAmntSpinner"); // NOI18N
        pherLadenAmntSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pherLadenAmntSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout nChoiceAntOptionsPanelLayout = new javax.swing.GroupLayout(nChoiceAntOptionsPanel);
        nChoiceAntOptionsPanel.setLayout(nChoiceAntOptionsPanelLayout);
        nChoiceAntOptionsPanelLayout.setHorizontalGroup(
            nChoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nChoiceAntOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pherLadenAmntLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pherLadenAmntSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        nChoiceAntOptionsPanelLayout.setVerticalGroup(
            nChoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nChoiceAntOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nChoiceAntOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pherLadenAmntLabel)
                    .addComponent(pherLadenAmntSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        antOptionsPanels.add(nChoiceAntOptionsPanel, "NChoiceAnt");

        javax.swing.GroupLayout adjConstantsPanelLayout = new javax.swing.GroupLayout(adjConstantsPanel);
        adjConstantsPanel.setLayout(adjConstantsPanelLayout);
        adjConstantsPanelLayout.setHorizontalGroup(
            adjConstantsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adjConstantsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pDecaySpinnerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pDecaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(antOptionsPanels, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        adjConstantsPanelLayout.setVerticalGroup(
            adjConstantsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adjConstantsPanelLayout.createSequentialGroup()
                .addGroup(adjConstantsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pDecaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pDecaySpinnerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(antOptionsPanels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout variablesPanelLayout = new javax.swing.GroupLayout(variablesPanel);
        variablesPanel.setLayout(variablesPanelLayout);
        variablesPanelLayout.setHorizontalGroup(
            variablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variablesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(variablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adjConstantsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        variablesPanelLayout.setVerticalGroup(
            variablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variablesPanelLayout.createSequentialGroup()
                .addComponent(adjConstantsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        mainPanel.add(variablesPanel, java.awt.BorderLayout.EAST);

        mainControlPanel.setName("mainControlPanel"); // NOI18N
        mainControlPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        viewScaleSlider.setMinimum(16);
        viewScaleSlider.setMinorTickSpacing(10);
        viewScaleSlider.setPaintLabels(true);
        viewScaleSlider.setPaintTicks(true);
        viewScaleSlider.setValue(30);
        viewScaleSlider.setName("viewScaleSlider"); // NOI18N
        viewScaleSlider.setPreferredSize(new java.awt.Dimension(100, 29));
        viewScaleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                viewScaleSliderStateChanged(evt);
            }
        });
        mainControlPanel.add(viewScaleSlider);

        stepDelayLabel.setLabelFor(stepDelaySpinner);
        stepDelayLabel.setText(resourceMap.getString("stepDelayLabel.text")); // NOI18N
        stepDelayLabel.setName("stepDelayLabel"); // NOI18N
        mainControlPanel.add(stepDelayLabel);

        stepDelaySpinner.setModel(new javax.swing.SpinnerNumberModel(0.1d, 0.0d, 1.0d, 0.01d));
        stepDelaySpinner.setName("stepDelaySpinner"); // NOI18N
        stepDelaySpinner.setPreferredSize(new java.awt.Dimension(70, 28));
        mainControlPanel.add(stepDelaySpinner);

        stepButton.setAction(actionMap.get("stepFoward")); // NOI18N
        stepButton.setForeground(resourceMap.getColor("stepButton.foreground")); // NOI18N
        stepButton.setText(resourceMap.getString("stepButton.text")); // NOI18N
        stepButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        stepButton.setName("stepButton"); // NOI18N
        mainControlPanel.add(stepButton);

        playButton.setAction(actionMap.get("play")); // NOI18N
        playButton.setText(resourceMap.getString("playButton.text")); // NOI18N
        playButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playButton.setName("playButton"); // NOI18N
        mainControlPanel.add(playButton);

        pauseButton.setAction(actionMap.get("pause")); // NOI18N
        pauseButton.setText(resourceMap.getString("pauseButton.text")); // NOI18N
        pauseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pauseButton.setName("pauseButton"); // NOI18N
        mainControlPanel.add(pauseButton);

        mainPanel.add(mainControlPanel, java.awt.BorderLayout.PAGE_END);

        mainScrollPane.setMinimumSize(new java.awt.Dimension(100, 100));
        mainScrollPane.setName("mainScrollPane"); // NOI18N
        mainScrollPane.setPreferredSize(new java.awt.Dimension(500, 500));
        mainPanel.add(mainScrollPane, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 682, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void viewScaleSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_viewScaleSliderStateChanged

        dsview.setScale(viewScaleSlider.getValue());
        //dsview.setPreferredSize(dsview.getPreferredSize());
        //dsview.invalidate();
        //dsview.repaint();
        //mainScrollPane.invalidate();
        //mainScrollPane.repaint();
        mainScrollPane.getViewport().setViewSize(dsview.getPreferredSize());

    }//GEN-LAST:event_viewScaleSliderStateChanged

    private void divergenceSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_divergenceSpinnerStateChanged
        TwoChoiceAnt.divergence=(Double)divergenceSpinner.getValue();
    }//GEN-LAST:event_divergenceSpinnerStateChanged

    private void pherLadenAmntSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pherLadenAmntSpinnerStateChanged
        NChoiceAnt.pheremoneWhenLaden=(Double)pherLadenAmntSpinner.getValue();
    }//GEN-LAST:event_pherLadenAmntSpinnerStateChanged


    @Action
    public void play(ActionEvent e)
      {

        stepButton.setEnabled(false);
        stepTimer.start();


        
      }


    @Action
    public void pause()
      {
        stepTimer.stop();
        stepButton.setEnabled(true);
      }


    @Action
    public void regenerateGrid()
      {
        pause();
        String antType = antTypeBox.getSelectedIndex()==0?"TwoChoiceAnt":"NChoiceAnt"; // FIX don't guess the value, check it
        dsview= new GridLayoutDSView(
                (Integer)gridWidthSpinner.getValue(),
                (Integer)gridHeightSpinner.getValue(),
                (Integer)antCountSpinner.getValue() ,
                antType,
                (Long)seedSpinner.getValue(),
                wrapEdgesCB.isSelected() );
        dsview.setMaxAnts((Integer)maxAntsSpinner.getValue());
        
        dsview.addMouseListenerToChildren(addFood);
        dsview.setScale(viewScaleSlider.getValue());
        mainScrollPane.setViewportView(dsview);
        ((CardLayout)antOptionsPanels.getLayout()).show(antOptionsPanels, antType);
        dsview.invalidate();

      }


    @Action
    public void randomizeSeedSpinner()
      {
        seedSpinner.setValue((new Random()).nextLong());
      }


    @Action
    public void depositFood()
      {
        Random rand = new Random();
        int qnt = (Integer)randFoodQntSpinner.getValue(),
            amnt = (Integer)randFoodAmntSpinner.getValue();
        double qntVar = (Double)randFoodQntVarSpinner.getValue(),
               amntVar = (Double)randFoodAmntVarSpinner.getValue();
        int qntVarAdj = (int)(qntVar*qnt),
            amntVarAdj =(int)(amntVar*amnt);
        for (int i=0; i<(qnt-qntVarAdj+rand.nextInt(2*qntVarAdj+1));i++){
            PheremoneDSN theNode = ((PheremoneDSN)dsview.getSpace().getNode(rand.nextInt(dsview.getSpace().getXwidth()), rand.nextInt(dsview.getSpace().getYwidth())));
            theNode.setFood(amnt-amntVarAdj+rand.nextInt(2*amntVarAdj+1));

        }

        dsview.invalidate();
        dsview.repaint();
      }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adjConstantsPanel;
    private javax.swing.JLabel antCountLabel;
    private javax.swing.JSpinner antCountSpinner;
    private javax.swing.JPanel antOptionsPanels;
    private javax.swing.JComboBox antTypeBox;
    private javax.swing.JLabel antTypeBoxLabel;
    private javax.swing.JSpinner clickDepositSpinner;
    private javax.swing.JLabel clickDepositSpinnerLabel;
    private javax.swing.JButton depositRandomFood;
    private javax.swing.JSpinner divergenceSpinner;
    private javax.swing.JLabel gridHeightLabel;
    private javax.swing.JSpinner gridHeightSpinner;
    private javax.swing.JLabel gridWidthLabel;
    private javax.swing.JSpinner gridWidthSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainControlPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JLabel maxAntsLabel;
    private javax.swing.JSpinner maxAntsSpinner;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel nChoiceAntOptionsPanel;
    private javax.swing.JSpinner pDecaySpinner;
    private javax.swing.JLabel pDecaySpinnerLabel;
    private javax.swing.JButton pauseButton;
    private javax.swing.JLabel pherLadenAmntLabel;
    private javax.swing.JSpinner pherLadenAmntSpinner;
    private javax.swing.JButton playButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel randFoodAmntLabel;
    private javax.swing.JSpinner randFoodAmntSpinner;
    private javax.swing.JLabel randFoodAmntVarLabel;
    private javax.swing.JSpinner randFoodAmntVarSpinner;
    private javax.swing.JLabel randFoodQntLabel;
    private javax.swing.JSpinner randFoodQntSpinner;
    private javax.swing.JLabel randFoodQntVarLabel;
    private javax.swing.JSpinner randFoodQntVarSpinner;
    private javax.swing.JButton seedButton;
    private javax.swing.JSpinner seedSpinner;
    private javax.swing.JLabel spacer5;
    private javax.swing.JLabel spacer6;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stepButton;
    private javax.swing.JLabel stepDelayLabel;
    private javax.swing.JSpinner stepDelaySpinner;
    private javax.swing.JPanel twoCoiceAntOptionsPanel;
    private javax.swing.JPanel variablesPanel;
    private javax.swing.JSlider viewScaleSlider;
    private javax.swing.JCheckBox wrapEdgesCB;
    // End of variables declaration//GEN-END:variables

    private MouseListener addFood = new MouseListener() {


            public void mouseClicked(MouseEvent e)
              {
                  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "mouse click recieved. Source:"+e.toString() );
                if ( e.getSource() instanceof GridDSLabel)
                    {
                      GridDSLabel clickedLabel = (GridDSLabel) e.getSource();
                      clickedLabel.getNode().setFood((Integer)clickDepositSpinner.getValue());
                      clickedLabel.invalidate();
                      clickedLabel.repaint();
                    }

              }


            public void mousePressed(MouseEvent e)
              {
                //Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "mouse click recieved. Source:"+e.toString() );
                //throw new UnsupportedOperationException("Not supported yet.");
              }


            public void mouseReleased(MouseEvent e)
              {
                //throw new UnsupportedOperationException("Not supported yet.");
              }


            public void mouseEntered(MouseEvent e)
              {
                //throw new UnsupportedOperationException("Not supported yet.");
              }


            public void mouseExited(MouseEvent e)
              {
                //throw new UnsupportedOperationException("Not supported yet.");
              }
        };

    private final javax.swing.ActionMap actionMap ;
    private GridLayoutDSView dsview;
    //private TiledDiscreteSpace space;
    //private Vector<Ant> ants;
    private Timer stepTimer;
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
