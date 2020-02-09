<?php

namespace EshopBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('EshopBundle:Front:index.html.twig');
    }


    public function backAction()
    {
        return $this->render('EshopBundle:Back:index.html.twig');
    }
}
